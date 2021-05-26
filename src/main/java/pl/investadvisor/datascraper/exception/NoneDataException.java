package pl.investadvisor.datascraper.exception;

import static java.lang.String.format;

public class NoneDataException extends RuntimeException {
    public NoneDataException(String message, Object... arguments) {
        super(format(message, arguments));
    }
}
