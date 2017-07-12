package main.speechrecognition.recognizers.google;

import com.google.api.gax.grpc.ApiStreamObserver;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import main.speechrecognition.notification.SpeechNotifier;
import main.speechrecognition.notification.SpeechObservable;
import main.speechrecognition.notification.SpeechObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alvaro on 7/12/17.
 */
public class GoogleResponseApiStreamingObserver <T> implements ApiStreamObserver<T>, SpeechObservable {
    private final List<T> messages = new java.util.ArrayList<T>();
    private  SpeechNotifier notifier;
    private final GoogleRecognition recognition;

    public GoogleResponseApiStreamingObserver(GoogleRecognition recognition) {
        this.notifier = new SpeechNotifier();
        this.recognition = recognition;
    }

    public SpeechNotifier getNotifier(){
        return notifier;
    }

    public void setNotifier(SpeechNotifier notifier){
        this.notifier = notifier;
    }

    @Override
    public void onNext(T message) {
        messages.add(message);
        String spokenText = parseResults((StreamingRecognizeResponse) message);
        if(!spokenText.isEmpty()) {
            notifySpeechObservers(spokenText);
            onCompleted();
            this.recognition.restart();
        }

    }

    private String parseResults(StreamingRecognizeResponse message) {
        StringBuilder spokenText = new StringBuilder();
        for (StreamingRecognitionResult result: message.getResultsList()) {
            for (SpeechRecognitionAlternative alternative:result.getAlternativesList()) {
                spokenText.append(alternative.getTranscript());
            }
        }
        return spokenText.toString();
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
    }


    @Override
    public void register(SpeechObserver observer) {
        notifier.register(observer);
    }

    @Override
    public void unregister(SpeechObserver observer) {
        notifier.unregister(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        notifier.notifySpeechObservers(spokenText);
    }
}
