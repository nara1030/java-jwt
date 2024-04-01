package org.among.usermodule.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "ID는 이메일 형식입니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 영어 대소문자, 숫자, 특수문자 중 3가지 이상을 만족하는 12자 이상이어야 합니다."),
    INVALID_EMAIL_VALUE(HttpStatus.UNAUTHORIZED, "해당 ID의 사용자가 존재하지 않습니다. ID를 다시 입력해주세요."),
    INVALID_PASSWORD_VALUE(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다. 비밀번호를 다시 입력해주세요."),
    EXISTING_EMAIL_VALUE(HttpStatus.BAD_REQUEST, "이미 존재하는 ID입니다. 이메일을 다시 입력해주세요.");;

    private final HttpStatus httpStatus;
    private final String message;
}
