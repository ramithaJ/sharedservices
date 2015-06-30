/**
 * 
 */
package com.wiley.gr.ace.authorservices.model;

/**
 * @author bgundavarapu
 *
 */
public class PersonDetails {
	private Integer userId;
	private String emailAddr;

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
