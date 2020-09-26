package jrkpi2;

import org.apache.commons.lang3.ArrayUtils;

public class Utils {
    public static class Format {
        public static final int SIGNED_8  = 0,
                                SIGNED_16 = 1,
                                FLOAT_32  = 2,
                                FLOAT_64  = 3;
    }

    // allowed set of sample rates
    static final int[] sampleRates = {
            8000,
            12000,
            22050,
            32000,
            44100,
            64000,
            96000,
            192000
    };

    static int getSampleRateIndex(int sampleRate) {
        return ArrayUtils.indexOf(sampleRates, sampleRate);
    }
}
