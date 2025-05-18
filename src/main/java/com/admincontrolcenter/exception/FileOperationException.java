package com.admincontrolcenter.exception;

public class FileOperationException extends RuntimeException {
    
    public FileOperationException(String message) {
        super(message);
    }
    
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static FileOperationException readError(String fileName, Throwable cause) {
        return new FileOperationException("Error reading from file: " + fileName, cause);
    }
    
    public static FileOperationException writeError(String fileName, Throwable cause) {
        return new FileOperationException("Error writing to file: " + fileName, cause);
    }
    
    public static FileOperationException createError(String fileName, Throwable cause) {
        return new FileOperationException("Error creating file: " + fileName, cause);
    }
}
