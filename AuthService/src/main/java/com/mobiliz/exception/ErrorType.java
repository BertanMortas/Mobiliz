package com.mobiliz.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parametre Hatası", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4100, "Kullancı adı veya şifre hatalı", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(4200, "Şifreler aynı değil", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4300, "Bu kullanıcı zaten kayıtlı", HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICATE(4350, "Bu email zaten kayıtlı", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "Böyle bir kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    ACTIVATE_CODE_ERROR(4500, "Aktivasyon kod hatası", HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR_MANAGER(4501, "Girmiş olduğunuz şirket adı sistemlerimizde bulunmamaktadır. Güvenlik amacıyla hesabınız askıya alınmıştır.", HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR_MANAGER2(4502, "Admin tarafından yapılacak olan onay henüz yapılmamıştır.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4600,"Token hatası" ,  HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(4700, "Token oluşturulamadı", HttpStatus.BAD_REQUEST),
    AUTHORIZATION_ERROR(4800, "you have no permission to continue", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
