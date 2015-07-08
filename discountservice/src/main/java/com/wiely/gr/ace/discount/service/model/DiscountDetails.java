package com.wiely.gr.ace.discount.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOUNT_DETAILS")
public class DiscountDetails {

	@Id
	@Column(name = "DISCOUNT_ID")
	int discountId;

	
	@Column(name = "PROMO_CODE")
	String promoCode;

	@Column(name = "DISCOUNT_TYPE_CODE")
	String discountTypeCode;

	@Column(name = "DISCOUNT_VALUE_TYPE")
	String discountValueType;

	@Column(name = "DISCOUNT_TYPE")
	String discountType;

	@Column(name = "DISCOUNT_VALUE")
	String discountValue;

	@Column(name = "START_DATE")
	String startdate;

	@Column(name = "END_DATE")
	String endDate;

	@Column(name = "ARTICLE_PUBLISHED_DATE")
	String articlePublisheddate;

	@Column(name = "COUNTRY_CD")
	String countryCode;

	@Column(name = "SOCIETY_CD")
	String societyCode;

	@Column(name = "JOURNAL_ACRONYM")
	String journalAcronym;

	@Column(name = "JOURNAL_TYPE")
	String journalType;

	@Column(name = "INSTITUTION_CODE")
	String institutionCode;

	@Column(name = "INSTITUTION_NAME")
	String institutionName;

	
	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getDiscountTypeCode() {
		return discountTypeCode;
	}

	public void setDiscountTypeCode(String discountTypeCode) {
		this.discountTypeCode = discountTypeCode;
	}

	public String getDiscountValueType() {
		return discountValueType;
	}

	public void setDiscountValueType(String discountValueType) {
		this.discountValueType = discountValueType;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getArticlePublisheddate() {
		return articlePublisheddate;
	}

	public void setArticlePublisheddate(String articlePublisheddate) {
		this.articlePublisheddate = articlePublisheddate;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getSocietyCode() {
		return societyCode;
	}

	public void setSocietyCode(String societyCode) {
		this.societyCode = societyCode;
	}

	public String getJournalAcronym() {
		return journalAcronym;
	}

	public void setJournalAcronym(String journalAcronym) {
		this.journalAcronym = journalAcronym;
	}

	public String getJournalType() {
		return journalType;
	}

	public void setJournalType(String journalType) {
		this.journalType = journalType;
	}

	public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	
}
