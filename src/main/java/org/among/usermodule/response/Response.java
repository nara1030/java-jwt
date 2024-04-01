package org.among.usermodule.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Response<T> {
    private final int resultCode;
    private final T result;

    public static Response<String> error(int resultCode, String errorMessage) {
        return new Response<>(resultCode, errorMessage);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>(HttpStatus.OK.value(), result);
    }
}
