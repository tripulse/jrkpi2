package jrkpi2;

import jrkpi2.Utils.Format;

import java.io.IOException;
import java.io.OutputStream;

public class Encoder {
    private final OutputStream out;
    private final int numChannels;

    public Encoder(OutputStream out, int format, int sampleRate, int numChannels)
            throws IOException, IllegalArgumentException
    {
        this.out = out;
        this.numChannels = numChannels;

        if(format != Format.SIGNED_8 &&
           format != Format.SIGNED_16 &&
           format != Format.FLOAT_32 &&
           format != Format.FLOAT_64)
            throw new IllegalArgumentException("Sample format must be one of Signed 8-bit, Signed 16-bit, " +
                                               "Float 32-bit, Float 64-bit");

        int sampleRateIndex = Utils.getSampleRateIndex(sampleRate);
        if(sampleRateIndex == -1)
            throw new IllegalArgumentException("Sample rate must be one of 8000, 12000, 22050, 32000, " +
                                               "44100, 64000, 96000, 192000");

        if(numChannels < 1 || numChannels > 8)
            throw new IllegalArgumentException("Number of channels must not be greater than 8 or lesser than 1");

        out.write(187);  // signature to detect validness.
        out.write(format << 6 | sampleRateIndex << 3 | numChannels);
    }
}
