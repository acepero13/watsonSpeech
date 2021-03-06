package vad.moannar.features.shortterm;

import be.tarsos.dsp.AudioEvent;
import vad.moannar.Feature;
import vad.moannar.util.Amplitude;

/**
 * Created by alvaro on 7/8/17.
 * the most dominant frequency component of the speech frame spectrum can be very useful in discriminating
 * between speech and silence frames
 */
public class DominatFrequency implements Feature {

    private final Amplitude amplitude;

    public DominatFrequency(AudioEvent audioEvent) {
        this.amplitude = new Amplitude(audioEvent);
    }

    @Override
    public double calculate() {
        return getDominantFrequency();
    }

    private double getDominantFrequency() {
        double maxMagnitude = Double.MIN_VALUE;
        int maxPos = 0;
        int counter = 0;
        for (float magnitude : amplitude.getAmplitudes()) {
            if (magnitude > maxMagnitude) {
                maxMagnitude = magnitude;
                maxPos = counter;
            }
            counter++;
        }
        return amplitude.getFrequencyFromBin(maxPos);
    }
}
