package vad.moannar.features;

/**
 * Created by alvaro on 7/9/17.
 */
public class MinFeatures {

    private  double minEnergy = 0;

    public void setMinEnergy(double minEnergy) {
        this.minEnergy = minEnergy;
    }

    public void setMinDominantFrequency(double minDominantFrequency) {
        this.minDominantFrequency = minDominantFrequency;
    }

    public void setMinSFM(double minSFM) {
        this.minSFM = minSFM;
    }

    private  double minDominantFrequency = 0;
    private  double minSFM = 0;

    public double getMinEnergy() {
        return minEnergy;
    }

    public double getMinDominantFrequency() {
        return minDominantFrequency;
    }

    public double getMinSFM() {
        return minSFM;
    }

    public void setMinimums(double frameEnergy, double frameDominantFrequency, double frameSfm) {
        minEnergy = frameEnergy;
        minDominantFrequency = frameDominantFrequency;
        minSFM = frameSfm;
    }

    public void calculateMinFeatures(double frameEnergy, double frameDominantFrequency, double frameSfm) {
        if(frameEnergy < minEnergy)
            setMinEnergy(frameEnergy);
        if(frameDominantFrequency < minDominantFrequency)
            setMinDominantFrequency(frameDominantFrequency);
        if(frameSfm < minSFM)
            setMinSFM(frameSfm);
    }
}
