package com.wiley.gr.ace.sharedservices.exceptions;

/**
 * The Class ResourceNotFoundException.
 */
public class ResourceNotFoundException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new resource not found exception.
     *
     * @param message
     *            the message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
