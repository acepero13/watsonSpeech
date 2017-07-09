package vad.moannar.features;

import be.tarsos.dsp.AudioEvent;
import vad.moannar.Feature;

/**
 * Created by alvaro on 7/9/17.
 */
public class Features {
    public static final int ENERGY_PRIM_THRESHOLD = 40;
    public static final double FREQ_PRIM_THRESHOLD = 185;
    public static final int SFM_PRIM_THRESHOLD = 5;
    private final MinFeatures minimums;

    private Feature energy;
    private Feature dominantFrequency;
    private Feature sfm;


    private double frameEnergy = 0;
    private double frameDominantFrequency = 0;
    private double frameSfm = 0;

    private boolean isFirstFrame = true;

    public Features(AudioEvent audioEvent, MinFeatures minimums){
        energy = new Energy(audioEvent);
        dominantFrequency = new DominatFrequency(audioEvent);
        sfm = new SpectralFlatnessMeasure(audioEvent);
        isFirstFrame = false;
        this.minimums = minimums;
    }

    public Features(AudioEvent audioEvent, boolean isFirstFrame, MinFeatures minimums){
        energy = new Energy(audioEvent);
        dominantFrequency = new DominatFrequency(audioEvent);
        sfm = new SpectralFlatnessMeasure(audioEvent);
        this.isFirstFrame = isFirstFrame;
        this.minimums = minimums;
    }

    public int calculate(){
        calculateFeatures();
        return featureCounter();
    }

    private void calculateFeatures() {
        frameEnergy = energy.calculate();
        frameDominantFrequency = dominantFrequency.calculate();
        frameSfm = sfm.calculate();
    }

    private int featureCounter() {
        int counter = 0;
        double energyThreshold = ENERGY_PRIM_THRESHOLD * Math.log10(Math.abs(minimums.getMinEnergy()));
        if((frameEnergy - minimums.getMinEnergy()) > energyThreshold)
            counter++;
        if((frameDominantFrequency - minimums.getMinDominantFrequency()) > FREQ_PRIM_THRESHOLD)
            counter++;
        if((frameSfm - minimums.getMinSFM()) > SFM_PRIM_THRESHOLD)
            counter ++;
        return counter;
    }

    public int  calculateMinimum(){
        calculateFeatures();
        if(isFirstFrame){
            minimums.setMinimums(frameEnergy, frameDominantFrequency, frameSfm);
        }else{
            minimums.calculateMinFeatures(frameEnergy, frameDominantFrequency, frameSfm);
        }
        return featureCounter();
    }

}
