package jrkpi2;

import jrkpi2.Utils.Format;
import jrkpi2.errors.MisalignmentException;
import jrkpi2.errors.SampleFormatMismatch;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Encoder {
    private final OutputStream out;
    private final int format;
    private final int numChannels;

    public Encoder(OutputStream out, int format, int sampleRate, int numChannels) throws IOException
    {
        this.out = out;
        this.format = format;
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

    // just to prevent horrible code duplication.
    private void doEncodeChecks(int requiredFormat, int numSamples) throws SampleFormatMismatch, MisalignmentException {
        if(format != requiredFormat)
            throw new SampleFormatMismatch("Configured format was " +
                    Utils.getSampleFormatName(format));

        if(numSamples % numChannels != 0)
            throw new MisalignmentException("Number of samples is misaligned " + numSamples);
    }

    public void encode(byte[] samples) throws IOException, SampleFormatMismatch, MisalignmentException {
        doEncodeChecks(Format.SIGNED_8, samples.length);

        out.write(samples);
    }

    public void encode(short[] samples) throws IOException, SampleFormatMismatch, MisalignmentException {
        doEncodeChecks(Format.SIGNED_16,samples.length);

        ByteBuffer buffer = ByteBuffer
                .allocate(samples.length * 2)
                .order(ByteOrder.BIG_ENDIAN);

        buffer.asShortBuffer().put(samples);
        out.write(buffer.array());
    }

    public void encode(float[] samples) throws IOException, SampleFormatMismatch, MisalignmentException {
        doEncodeChecks(Format.FLOAT_32,samples.length);

        ByteBuffer buffer = ByteBuffer
                .allocate(samples.length * 4)
                .order(ByteOrder.BIG_ENDIAN);

        buffer.asFloatBuffer().put(samples);
        out.write(buffer.array());
    }

    public void encode(double[] samples) throws IOException, SampleFormatMismatch, MisalignmentException {
        doEncodeChecks(Format.FLOAT_64,samples.length);

        ByteBuffer buffer = ByteBuffer
                .allocate(samples.length * 8)
                .order(ByteOrder.BIG_ENDIAN);

        buffer.asDoubleBuffer().put(samples);
        out.write(buffer.array());
    }

    public void flush() throws IOException {
        out.flush();
    }

    public void close() throws IOException {
        out.close();
    }
}
