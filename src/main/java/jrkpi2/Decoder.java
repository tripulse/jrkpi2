package jrkpi2;

import jrkpi2.errors.InvalidDataException;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Decoder extends FilterInputStream {
    private final int format;
    private final int sampleRate;
    private final int numChannels;

    public Decoder(InputStream in) throws IOException, InvalidDataException {
        super(in);

        byte[] header = new byte[2];
        if(in.read(header) != 2)
            throw new IOException("Insufficient data for parsing RKPI2 header");

        if(header[0] != (byte)187)
            throw new InvalidDataException("RKPI2 Header signature mismatch");

        int headerFields = Byte.toUnsignedInt(header[1]);

        format      = headerFields >> 6;
        sampleRate  = Utils.sampleRates[headerFields >> 3 & 7];
        numChannels = headerFields & 7;
    }

    public int getFormat() {
        return format;
    }

    public int getNumChannels() {
        return numChannels;
    }

    public int getSampleRate() {
        return sampleRate;
    }
}
