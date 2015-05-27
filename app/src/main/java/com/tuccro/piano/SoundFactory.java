package com.tuccro.piano;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SoundFactory {

    private double frequency;
    private double duration;
    private int sampleRate = 24000;


    private int numSamples;
    private double sample[];
    private byte generatedSnd[];

    public SoundFactory(double frequencyOfSound, double timeInSeconds) {
        this.frequency = frequencyOfSound;
        this.duration = timeInSeconds;
    }

    void play(){
        init();
        genTone();
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

    void init() {
        numSamples = (int) duration * sampleRate;
        sample = new double[numSamples];
        generatedSnd = new byte[2 * numSamples];
    }

    void genTone() {
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / frequency));
        }
        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }


}
