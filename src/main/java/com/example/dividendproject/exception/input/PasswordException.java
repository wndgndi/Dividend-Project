package com.example.dividendproject.exception.input;

import com.example.dividendproject.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class PasswordException extends AbstractException {

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "비밀번호가 일치하지 않습니다.";
    }
}
