package com.laya.ExcelVersion.exception;

public class CorruptedFileException extends RuntimeException {
    public CorruptedFileException(String message) {
        super(message);
    }
}
