package jrkpi2;

import jrkpi2.Utils.Format;
import jrkpi2.errors.InvalidDataException;
import jrkpi2.errors.SampleFormatMismatch;

import java.io.IOException;
import java.io.InputStream;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.util.Arrays;

public class Decoder {
    private final InputStream in;

    private final int format;
    private final int sampleRate;
    private final int numChannels;

    public Decoder(InputStream in) throws IOException, InvalidDataException {
        this.in = in;

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

    private byte[] readAligned(int numSamples) throws IOException {
        int blockSize = numChannels * Utils.getFormatSize(format);
        byte[] buffer = new byte[numSamples * blockSize];

        // align to the closest possible sample-block boundary.
        return Arrays.copyOf(buffer, blockSize * (in.read(buffer) / blockSize));
    }

    public byte[] decodeInt8(int numSamples) throws IOException, SampleFormatMismatch {
        if(format != Format.SIGNED_8)
            throw new SampleFormatMismatch("Samples are not to be interpreted as Signed 8-bit");

        return readAligned(numSamples);
    }

    public short[] decodeInt16(int numSamples) throws IOException, SampleFormatMismatch {
        if(format != Format.SIGNED_16)
            throw new SampleFormatMismatch("Samples are not to be interpreted as Signed 16-bit");

        return ByteBuffer.wrap(readAligned(numSamples))
                         .order(ByteOrder.BIG_ENDIAN)
                         .asShortBuffer()
                         .array();
    }

    public float[] decodeFloat32(int numSamples) throws IOException, SampleFormatMismatch {
        if(format != Format.FLOAT_32)
            throw new SampleFormatMismatch("Samples are not to be interpreted as Float 32-bit");

        return ByteBuffer.wrap(readAligned(numSamples))
                .order(ByteOrder.BIG_ENDIAN)
                .asFloatBuffer()
                .array();
    }

    public double[] decodeFloat64(int numSamples) throws IOException, SampleFormatMismatch {
        if(format != Format.FLOAT_64)
            throw new SampleFormatMismatch("Samples are not to be interpreted as Float 64-bit");

        return ByteBuffer.wrap(readAligned(numSamples))
                .order(ByteOrder.BIG_ENDIAN)
                .asDoubleBuffer()
                .array();
    }

    public void close() throws IOException {
        in.close();
    }
}
