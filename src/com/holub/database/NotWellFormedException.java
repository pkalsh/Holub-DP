package com.holub.database;

public class NotWellFormedException extends Throwable {
    public NotWellFormedException() {
        super("잘못된 형식의 파일입니다.\n");
    }
}
