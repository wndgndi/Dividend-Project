package com.example.dividendproject.exception.input;

import com.example.dividendproject.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class NoUsernameException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 ID 입니다.";
    }
}
