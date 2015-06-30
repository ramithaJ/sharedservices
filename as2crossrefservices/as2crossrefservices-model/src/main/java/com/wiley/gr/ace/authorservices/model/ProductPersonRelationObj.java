package com.wiley.gr.ace.authorservices.model;

import java.util.List;

public class ProductPersonRelationObj {

	private Integer dhId;
	private List<PersonDetails> coAuthorList;
	private PersonDetails correspondingAuthor;

	public Integer getDhId() {
		return dhId;
	}

	public void setDhId(Integer dhId) {
		this.dhId = dhId;
	}

	public final List<PersonDetails> getCoAuthorList() {
		return coAuthorList;
	}

	public final void setCoAuthorList(List<PersonDetails> coAuthorList) {
		this.coAuthorList = coAuthorList;
	}

	public final PersonDetails getCorrespondingAuthor() {
		return correspondingAuthor;
	}

	public final void setCorrespondingAuthor(PersonDetails correspondingAuthor) {
		this.correspondingAuthor = correspondingAuthor;
	}

}
