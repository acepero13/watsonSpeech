package main.speechrecognition.audioproviders.microphone.recordaudio.event;

import com.google.protobuf.ByteString;

/**
 * Created by alvaro on 7/12/17.
 */
public class ByteStringConverter implements DataEventConverter{

    private final byte[] data;

    public ByteStringConverter(byte[] data) {
        this.data = data;
    }

    @Override
    public ByteString convert() {
        return ByteString.copyFrom(data, 0, data.length);
    }
}
