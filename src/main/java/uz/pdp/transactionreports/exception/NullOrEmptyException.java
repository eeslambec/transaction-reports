package uz.pdp.transactionreports.exception;

public class NullOrEmptyException extends RuntimeException {
    public NullOrEmptyException(String message) {
        super(message + " is null or empty");
    }
}
