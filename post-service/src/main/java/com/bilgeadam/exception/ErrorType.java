package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_SERVER_ERROR(5300, "Sunucu Hatasi",HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST (4300,"Parametre hatasi", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4310,"Böyle bir kullanici bulunamadi...",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4311,"Geçersiz token" ,HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatus httpStatus;
}
