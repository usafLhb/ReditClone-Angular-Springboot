package com.Clone.Clone.exception;

import java.util.function.Supplier;

public class PostNotFoundException extends Throwable {
    public PostNotFoundException(String exMessage) {
        super(exMessage);
    }


}
