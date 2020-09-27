package jrkpi2.errors;

/**
 * Data of some sort was not like as desired to be.
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
