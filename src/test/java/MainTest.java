import jrkpi2.Decoder;
import jrkpi2.Utils.Format;
import jrkpi2.Encoder;

import jrkpi2.errors.MisalignmentException;
import jrkpi2.errors.SampleFormatMismatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MainTest {
    private final Decoder decoder;
    
    public MainTest() throws IOException, SampleFormatMismatch, MisalignmentException {
        File ioTempFile = File.createTempFile("jrkpi2", ".c2");

        new Encoder(
                new FileOutputStream(ioTempFile),
                Format.SIGNED_8,
                8000,
                1)
                .encode(new byte[]{127, -61, 8, 48, 15});

        decoder = new Decoder(new FileInputStream(ioTempFile));
    }

    @Test
    public void headerInterchange() {
        assertEquals(decoder.getFormat(), Format.SIGNED_8);
        assertEquals(decoder.getSampleRate(), 8000);
        assertEquals(decoder.getNumChannels(), 1);
    }

    @Test
    public void sampleDataInterchange() throws IOException, SampleFormatMismatch {
        assertArrayEquals(
                decoder.decodeInt8(5),
                new byte[]{127, -61, 8, 48, 15});
    }
}
