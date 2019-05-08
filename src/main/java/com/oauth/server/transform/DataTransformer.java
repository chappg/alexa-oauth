package com.oauth.server.transform;

public interface DataTransformer<O, I> {

    O transform(I input);
}
