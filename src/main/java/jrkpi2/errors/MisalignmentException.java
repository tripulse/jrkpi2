package jrkpi2.errors;

/**
 * Misaligned sample block, an aligned boundary is of <em>number of channels
 * * number of bytes for a sample</em> bytes.
 */
public class MisalignmentException extends Exception {
    public MisalignmentException(String message) {
        super(message);
    }
}
