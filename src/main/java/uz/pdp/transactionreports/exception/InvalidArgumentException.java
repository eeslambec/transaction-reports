package uz.pdp.transactionreports.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String m){
        super(m + " is invalid");
    }
}
