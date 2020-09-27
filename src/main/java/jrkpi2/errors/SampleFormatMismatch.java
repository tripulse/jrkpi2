package jrkpi2.errors;

/**
 * Encoding or decoding sample data format mismatches than what is
 * configured or comprehended.
 */
public class SampleFormatMismatch extends Exception {
    public SampleFormatMismatch(String message) {
        super(message);
    }
}
