import jrkpi2.Decoder;
import jrkpi2.Utils.Format;
import jrkpi2.Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MainTest {
    @Test
    public void headerInterchange() throws IOException {
        File ioTempFile = File.createTempFile("jrkpi2", ".c2");

        new Encoder(
                new FileOutputStream(ioTempFile),
                Format.FLOAT_32,
                8000,
                1);

        Decoder decoder = new Decoder(new FileInputStream(ioTempFile));

        // do assertions to check interchangeability
        assertEquals(decoder.getFormat(), Format.FLOAT_32);
        assertEquals(decoder.getSampleRate(), 8000);
        assertEquals(decoder.getNumChannels(), 1);
    }
}
