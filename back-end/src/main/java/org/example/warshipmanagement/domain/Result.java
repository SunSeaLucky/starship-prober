package org.example.warshipmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private T data;
    private int code;
    private String msg;

    public static <E> Result<E> success(E data) {
        return new Result<E>(data, 200, "Response success!");
    }

    public static Result<String> success(String data) {
        return new Result<String>(data, 200, "Response success!");
    }

    public static Result<User> fail(String msg) {
        return new Result<User>(null, 100, msg);
    }
}
