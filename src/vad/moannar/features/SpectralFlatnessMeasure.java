package vad.moannar.features;

import be.tarsos.dsp.AudioEvent;
import vad.moannar.Feature;
import vad.moannar.util.Amplitude;

/**
 * Created by alvaro on 7/8/17.
 * Spectral Flatness is a measure of the noisiness of spectrum
 * and is a good feature in Voiced/Unvoiced/Silence detection
 */
public class SpectralFlatnessMeasure implements Feature {

    private final float[] amplitudes;

    public SpectralFlatnessMeasure(AudioEvent audioEvent) {
        Amplitude amplitude = new Amplitude(audioEvent);
        amplitudes = amplitude.getAmplitudes();
    }

    @Override
    public double calculate() {
        return Math.abs(10 * Math.log10(calculateGeometricMean() / calculateArithmeticMean()));
    }

    private double calculateArithmeticMean() {
        if (isBufferEmpty()) {
            return 0;
        }
        return artihmeticMean();
    }

    private double calculateGeometricMean() {
        if (isBufferEmpty()) {
            return 1;
        }
        return Math.pow(10, geometricMean());
    }

    private double geometricMean() {
        return logSum() / (float) amplitudes.length;
    }

    private double logSum() {
        double sum = 0;
        for (float value : amplitudes) {
            sum += Math.log10(value);
        }
        return sum;
    }

    private boolean isBufferEmpty() {
        return amplitudes == null || amplitudes.length <= 0;
    }

    private double artihmeticMean() {
        double sumValue = sum();
        return sumValue / (float) amplitudes.length;
    }

    private double sum() {
        double sum = 0;
        for (float value : amplitudes) {
            sum += value;
        }
        return sum;
    }
}
