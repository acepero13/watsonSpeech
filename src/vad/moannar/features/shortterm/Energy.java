package vad.moannar.features.shortterm;

import be.tarsos.dsp.AudioEvent;
import vad.moannar.Feature;

/**
 * Created by alvaro on 7/8/17.
 * <p>
 * Energy is the most common feature for speech/silence
 * detection. However this feature loses its efficiency in noisy conditions especially in lower SNRs.
 */
public class Energy implements Feature {

    private final float[] buffer;

    public Energy(AudioEvent audioEvent) {

        this.buffer = audioEvent.getFloatBuffer().clone();

    }

    public double calculate() {
        return Math.pow(localEnergy(), 0.5);
    }


    private double localEnergy() {
        double power = 0.0D;
        for (float element : buffer) {
            element /= (1.0f / 32767.0f);
            power += element * element;
        }
        return power;
    }
}
