package main.speechrecognition.recognizers.google;


import com.google.api.gax.grpc.ApiStreamObserver;
import com.google.api.gax.grpc.StreamingCallable;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import main.speechrecognition.audioproviders.Audible;
import main.speechrecognition.audioproviders.microphone.Microphone;
import main.speechrecognition.audioproviders.microphone.recordaudio.RecordAudioObserver;
import main.speechrecognition.audioproviders.microphone.recordaudio.event.RecordAudioEvent;
import main.speechrecognition.notification.SpeechNotifier;
import main.speechrecognition.notification.SpeechObservable;
import main.speechrecognition.notification.SpeechObserver;

import java.io.IOException;
import java.util.List;

/**
 * Created by alvaro on 7/12/17.
 */
public class GoogleRecognition implements SpeechObservable, RecordAudioObserver, Audible{

    private RecognitionConfig config;
    private RecognitionAudio audio;
    private SpeechClient speech;
    private SpeechSettings settings;
    private GoogleResponseApiStreamingObserver<StreamingRecognizeResponse> responseObserver;
    private StreamingCallable<StreamingRecognizeRequest, StreamingRecognizeResponse> callable;
    private ApiStreamObserver<StreamingRecognizeRequest> requestObserver;
    private StreamingRecognitionConfig streamingConfig;

    private final Thread microphoneThread;
    private SpeechNotifier notifier;


    public GoogleRecognition(){
        Microphone microphone = new Microphone();
        microphone.register(this);
        microphoneThread = new Thread(microphone);
    }


    public void init() throws IOException {

        speech = SpeechClient.create();
        createConfiguration();
        initRequest();
        requestObserver.onNext(StreamingRecognizeRequest.newBuilder()
                .setStreamingConfig(streamingConfig)
                .build());
        handleNotifier();
    }

    private void handleNotifier() {
        if(notifier == null){
            notifier = responseObserver.getNotifier();
        }else {
            responseObserver.setNotifier(notifier);
        }
    }

    public void close(){
        try {
            speech.close();
            responseObserver = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRequest() {
        responseObserver = new GoogleResponseApiStreamingObserver<StreamingRecognizeResponse>(this);
        callable = speech.streamingRecognizeCallable();
        requestObserver = callable.bidiStreamingCall(responseObserver);
    }

    private void createConfiguration() {
        config = RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setLanguageCode("en-US")
                .setSampleRateHertz(16000)
                .build();

        streamingConfig = StreamingRecognitionConfig.newBuilder()
                .setConfig(config)
                .setInterimResults(false)
                .setSingleUtterance(true)
                .build();
    }

    private void recognize(ByteString data) {
        if(isRecognitionReady()) {
            requestObserver.onNext(StreamingRecognizeRequest.newBuilder()
                    .setAudioContent(data)
                    .build());
        }
    }

    private boolean isRecognitionReady() {
        return requestObserver!=null;
    }

    public void restart(){
        close();
        tryToInit();
    }

    private void tryToInit() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void register(SpeechObserver observer) {
        responseObserver.register(observer);
    }

    @Override
    public void unregister(SpeechObserver observer) {
        responseObserver.unregister(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        responseObserver.notifySpeechObservers(spokenText);
    }

    @Override
    public void update(RecordAudioEvent event) {
        recognize((ByteString) event.getData());
    }

    @Override
    public void startListening() {
        startMicrophone();
        tryToInit();
    }

    private void startMicrophone() {
        microphoneThread.start();
    }


    @Override
    public void stopListening() {
        requestObserver.onCompleted();
    }



}
