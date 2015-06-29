/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiles.gr.ace.discount.exception;

/**
 * @author virtusa
 *	version 1.0
 */
public class ASException extends RuntimeException {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private Throwable t;
    private String description;
    private Object[] msgPart;
    
    public ASException(String errorCode, Throwable t) {
        this.errorCode = errorCode;
        this.t = t;
    }
    
    public ASException(String errorCode, String desc, Throwable t) {
        this.errorCode = errorCode;
        this.description = desc;
        this.t = t;
    }
    
    public ASException(String errorCode, String desc) {
        this.errorCode = errorCode;
        this.description = desc;
    }
    
    public ASException(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public ASException(String errorCode, Object[] msgPart) {
        this.errorCode = errorCode;
        this.msgPart = msgPart;
    }
    
    public ASException(Throwable t) {
        this.t = t;
    }
    
    public ASException() {
        // TODO Auto-generated constructor stub
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public Object[] getMsgPart() {
        return msgPart;
    }
    
    public void setMsgPart(Object[] msgPart) {
        this.msgPart = msgPart;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Throwable getWrappedException() {
        // Not all ASException will have Wrapped Exception, Inorder to make
        // Generic if Wrapped
        // Exception is not found, send current ASException Object itself
        if (t != null)
            return t;
        else
            return this;
    }
    
}
