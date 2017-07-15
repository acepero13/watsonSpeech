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
import main.speechrecognition.recognizers.SpeechRecognition;

import java.io.IOException;
import java.util.List;

/**
 * Created by alvaro on 7/12/17.
 */
public class GoogleRecognition extends SpeechRecognition implements  RecordAudioObserver{

    private final Thread microphoneThread;
    private RecognitionConfig config;
    private SpeechClient speech;
    private GoogleResponseApiStreamingObserver<StreamingRecognizeResponse> responseObserver;
    private StreamingCallable<StreamingRecognizeRequest, StreamingRecognizeResponse> callable;
    private ApiStreamObserver<StreamingRecognizeRequest> requestObserver;
    private StreamingRecognitionConfig streamingConfig;
    private boolean isListening = false;

    public GoogleRecognition(){
        super();
        Microphone microphone = new Microphone();
        microphone.register(this);
        microphoneThread = new Thread(microphone);
    }

    @Override
    public boolean isListening() {
        return isListening;
    }


    public void init() throws IOException {
        speech = SpeechClient.create();
        createConfiguration();
        initRequest();
        requestObserver.onNext(StreamingRecognizeRequest.newBuilder()
                .setStreamingConfig(streamingConfig)
                .build());
    }



    private void close(){
        try {
            speech.close();
            responseObserver = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRequest() {
        responseObserver = new GoogleResponseApiStreamingObserver<StreamingRecognizeResponse>(this, notifier);
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
            isListening = true;

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
    public void update(RecordAudioEvent event) {
        recognize((ByteString) event.getData());
    }

    @Override
    public void startListening() {
        startMicrophone();
        tryToInit();
    }

    @Override
    public void stopRecognition() {
        if(speech ==null){
            return;
        }
        if(responseObserver!= null && responseObserver.hasBeenProcessed())
            requestObserver.onCompleted();
        isListening = false;
        close();
    }

    private void startMicrophone() {
        microphoneThread.start();
    }

}
