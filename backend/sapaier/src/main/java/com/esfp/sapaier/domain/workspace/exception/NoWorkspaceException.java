package com.esfp.sapaier.domain.workspace.exception;

public class NoWorkspaceException extends RuntimeException{

    public NoWorkspaceException(String message) {
        super(message);
    }

    public NoWorkspaceException(String message, Throwable cause) {
        super(message, cause);
    }

}
