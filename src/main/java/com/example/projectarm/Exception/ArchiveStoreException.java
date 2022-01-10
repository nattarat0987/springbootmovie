package com.example.projectarm.Exception;

public class ArchiveStoreException extends  RuntimeException{

    public ArchiveStoreException(String message) {
        super(message);
    }

    public ArchiveStoreException(String message, Throwable exception) {
        super(message, exception);
    }
}
