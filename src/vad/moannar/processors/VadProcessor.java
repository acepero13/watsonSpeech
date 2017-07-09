package vad.moannar.processors;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import vad.moannar.features.Features;
import vad.moannar.features.MinFeatures;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by alvaro on 7/9/17.
 */
public class VadProcessor implements AudioProcessor {

    public static final int THIRTY_FRAME_MARK = 30;

    private int  frameCounter = 0;
    private Features features;
    private HashMap<Integer, Boolean> voicedFrame = new HashMap<>();
    private MinFeatures minFeatures = new MinFeatures();


    @Override
    public boolean process(AudioEvent audioEvent) {
        byte[] tmp = audioEvent.getByteBuffer();
        byte[] arr = {tmp[0], tmp[1] };
        Float f = ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        createFeatures(audioEvent);
        int featureCounter = calculateFeatures();
        System.out.println(featureCounter);
        if(featureCounter > 1){
            voicedFrame.put(frameCounter, true);
        }else{
            voicedFrame.put(frameCounter, false);
        }
        frameCounter++;
        return true;
    }

    private void createFeatures(AudioEvent audioEvent) {
        if(frameCounter == 0)
            features = new Features(audioEvent, true, minFeatures);
        else
            features = new Features(audioEvent, minFeatures);
    }

    private int calculateFeatures() {
        int frameCounter;
        if(isFirstThirtyFrames()){
            frameCounter = features.calculateMinimum();
        }else{
            frameCounter = features.calculate();
        }
        return frameCounter;
    }


    private boolean isFirstThirtyFrames() {
        return frameCounter < THIRTY_FRAME_MARK;
    }

    @Override
    public void processingFinished() {
        System.out.println(voicedFrame);
    }
}
