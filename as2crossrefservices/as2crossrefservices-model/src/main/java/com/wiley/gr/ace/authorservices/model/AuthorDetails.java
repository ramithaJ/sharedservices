package com.wiley.gr.ace.authorservices.model;

import java.util.List;

public class AuthorDetails {
	private String UserId;
	private String email;
	private List<ProductId> corresPondingAuthor;
	private List<ProductId> coAuthor;

	public final String getUserId() {
		return UserId;
	}

	public final void setUserId(String userId) {
		UserId = userId;
	}

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}

	public final List<ProductId> getCorresPondingAuthor() {
		return corresPondingAuthor;
	}

	public final void setCorresPondingAuthor(List<ProductId> corresPondingAuthor) {
		this.corresPondingAuthor = corresPondingAuthor;
	}

	public final List<ProductId> getCoAuthor() {
		return coAuthor;
	}

	public final void setCoAuthor(List<ProductId> coAuthor) {
		this.coAuthor = coAuthor;
	}
}
