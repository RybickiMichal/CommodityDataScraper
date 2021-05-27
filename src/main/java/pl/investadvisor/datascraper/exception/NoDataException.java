package pl.investadvisor.datascraper.exception;

import static java.lang.String.format;

public class NoDataException extends RuntimeException {
    public NoDataException(String message, Object... arguments) {
        super(format(message, arguments));
    }
}
