package jrkpi2.errors;

/**
 * {@code InvalidDataException} denotes data of some sort was not like as desired to be.
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
