package main.speechrecognition.audioproviders.microphone.recordaudio.event;



/**
 * Created by alvaro on 7/12/17.
 * //TODO: in case more daa types create factory
 */
public class MicrophoneOnReadEvent  implements RecordAudioEvent{
    private final byte[] data;
    private final DataEventConverter converter;

    public MicrophoneOnReadEvent(byte[] data) {

        this.data = data;
        this.converter = new ByteStringConverter(data);
    }

    @Override
    public Object getData() {
        return this.converter.convert();
    }
}
