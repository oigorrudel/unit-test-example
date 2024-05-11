package br.xksoberbado.unittestexample.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends RuntimeException {

    private String secondMessage;

    public BusinessException(final String message) {
        super(message);
        this.secondMessage = "Teest";
    }
}
