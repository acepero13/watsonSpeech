package vad.moannar.util;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.util.fft.FFT;
import vad.moannar.audio.AudioProvider;

/**
 * Created by alvaro on 7/8/17.
 */
public class Amplitude {

    private final AudioEvent audioEvent;
    private float[] amplitudes;
    private int bufferSize;
    private FFT fft;
    private float[] audioFloatBuffer;

    public Amplitude(AudioEvent audioEvent) {
        this.audioEvent = audioEvent;
        initAmplitudes();
        prepareBuffer();
        computeMagnitude();
    }

    private void prepareBuffer() {
        fft = new FFT(bufferSize);
        audioFloatBuffer = audioEvent.getFloatBuffer().clone();
    }

    private void initAmplitudes() {
        bufferSize = audioEvent.getBufferSize();
        amplitudes = new float[bufferSize / 2];
    }

    private void computeMagnitude() {
        fft.forwardTransform(audioFloatBuffer);
        fft.modulus(audioFloatBuffer, amplitudes);
    }

    public float[] getAmplitudes() {
        return amplitudes;
    }

    public int getFrequencyFromBin(int bin) {
        return (int) fft.binToHz(bin, AudioProvider.SAMPLE_RATE);
    }
}
