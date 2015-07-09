package com.wiley.gr.ace.sharedservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wiley.gr.ace.sharedservices.exceptions.ResourceNotFoundException;

/**
 * AbstractController handles errors.
 */
@RequestMapping(consumes = "application/json", produces = "application/json")
public abstract class AbstractController {

    /**
     * Handle exception.
     *
     * @param e
     *            the e
     * @return the error
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(RuntimeException e) {
        return new Error(e.getMessage());
    }

    /**
     * Handle exception.
     *
     * @param e
     *            the e
     * @return the error
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleException(ResourceNotFoundException e) {
        return new Error(e.getMessage());
    }

    /**
     * The Class Error.
     */
    public static class Error {

        /** The error. */
        private final String error;

        /**
         * Instantiates a new error.
         *
         * @param error
         *            the error
         */
        public Error(String error) {
            this.error = error;
        }

        /**
         * Gets the error.
         *
         * @return the error
         */
        public String getError() {
            return error;
        }
    }
}
