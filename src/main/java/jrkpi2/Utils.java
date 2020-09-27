package jrkpi2;

import org.apache.commons.lang3.ArrayUtils;

public class Utils {
    public static class Format {
        public static final int SIGNED_8 = 0,
                SIGNED_16 = 1,
                FLOAT_32 = 2,
                FLOAT_64 = 3;
    }

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

    static String getSampleFormatName(int format) {
        return switch (format) {
            case Format.SIGNED_8  -> "Signed 8-bit";
            case Format.SIGNED_16 -> "Signed 16-bit";
            case Format.FLOAT_32  -> "Float  32-bit";
            case Format.FLOAT_64  -> "Float  64-bit";
            default               -> "(Unknown)";
        };
    }

    /**
     * Number of bytes a sample of a format occupies.
     * @param format Format of the sample
     * @return Number of bytes will occupy
     */
    public static int getFormatSize(int format) {
        return switch (format) {
            case Format.SIGNED_8  -> 1;
            case Format.SIGNED_16 -> 2;
            case Format.FLOAT_32  -> 4;
            case Format.FLOAT_64  -> 8;
            default -> throw new IllegalArgumentException("Invalid RKPI2 format");
        };
    }
}
