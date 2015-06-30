package com.wiley.gr.ace.authorservices.persistence.entity;

// Generated Jun 26, 2015 10:24:59 AM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * JournalConfiguration generated by hbm2java
 */
@Entity
@Table(name = "JOURNAL_CONFIGURATION")
public class JournalConfiguration implements java.io.Serializable {

	private Integer dhId;
	private Users usersByCreatedBy;
	private Users usersByUpdatedBy;
	private Products products;
	private JournalSetupStates journalSetupStates;
	private String nickname;
	private String titleDispOoo;
	private Character invitedInAs;
	private Date invitedInAsStartDate;
	private Date invitedInAsEndDate;
	private Character dispForOoOrdering;
	private Character prodTrkPath;
	private Character coauthCanHaveFreeAccArt;
	private Character coauthCanSeeArtProdStages;
	private Character coauthCanSubstReviewProofs;
	private Character collgCanViewArtWol;
	private Character artCanHavePrdNonprdPath;
	private Character canCollConflIntrstForms;
	private Character coauthReceiveInvitation;
	private Character dispOrEmailMrktngToUsr;
	private Character dispOrEmailSurveyToUsr;
	private Character corrauCanHaveFreeAccArt;
	private Date createdDate;
	private Date updatedDate;

	public JournalConfiguration() {
	}

	public JournalConfiguration(Products products) {
		this.products = products;
	}

	public JournalConfiguration(Users usersByCreatedBy, Users usersByUpdatedBy,
			Products products, JournalSetupStates journalSetupStates,
			String nickname, String titleDispOoo, Character invitedInAs,
			Date invitedInAsStartDate, Date invitedInAsEndDate,
			Character dispForOoOrdering, Character prodTrkPath,
			Character coauthCanHaveFreeAccArt,
			Character coauthCanSeeArtProdStages,
			Character coauthCanSubstReviewProofs, Character collgCanViewArtWol,
			Character artCanHavePrdNonprdPath,
			Character canCollConflIntrstForms,
			Character coauthReceiveInvitation,
			Character dispOrEmailMrktngToUsr, Character dispOrEmailSurveyToUsr,
			Character corrauCanHaveFreeAccArt, Date createdDate,
			Date updatedDate) {
		this.usersByCreatedBy = usersByCreatedBy;
		this.usersByUpdatedBy = usersByUpdatedBy;
		this.products = products;
		this.journalSetupStates = journalSetupStates;
		this.nickname = nickname;
		this.titleDispOoo = titleDispOoo;
		this.invitedInAs = invitedInAs;
		this.invitedInAsStartDate = invitedInAsStartDate;
		this.invitedInAsEndDate = invitedInAsEndDate;
		this.dispForOoOrdering = dispForOoOrdering;
		this.prodTrkPath = prodTrkPath;
		this.coauthCanHaveFreeAccArt = coauthCanHaveFreeAccArt;
		this.coauthCanSeeArtProdStages = coauthCanSeeArtProdStages;
		this.coauthCanSubstReviewProofs = coauthCanSubstReviewProofs;
		this.collgCanViewArtWol = collgCanViewArtWol;
		this.artCanHavePrdNonprdPath = artCanHavePrdNonprdPath;
		this.canCollConflIntrstForms = canCollConflIntrstForms;
		this.coauthReceiveInvitation = coauthReceiveInvitation;
		this.dispOrEmailMrktngToUsr = dispOrEmailMrktngToUsr;
		this.dispOrEmailSurveyToUsr = dispOrEmailSurveyToUsr;
		this.corrauCanHaveFreeAccArt = corrauCanHaveFreeAccArt;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "products"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "DH_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getDhId() {
		return this.dhId;
	}

	public void setDhId(Integer dhId) {
		this.dhId = dhId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	public Users getUsersByCreatedBy() {
		return this.usersByCreatedBy;
	}

	public void setUsersByCreatedBy(Users usersByCreatedBy) {
		this.usersByCreatedBy = usersByCreatedBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
	public Users getUsersByUpdatedBy() {
		return this.usersByUpdatedBy;
	}

	public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
		this.usersByUpdatedBy = usersByUpdatedBy;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Products getProducts() {
		return this.products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SETUP_STATE_CD_IN_AS")
	public JournalSetupStates getJournalSetupStates() {
		return this.journalSetupStates;
	}

	public void setJournalSetupStates(JournalSetupStates journalSetupStates) {
		this.journalSetupStates = journalSetupStates;
	}

	@Column(name = "NICKNAME", length = 250)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "TITLE_DISP_OOO", length = 250)
	public String getTitleDispOoo() {
		return this.titleDispOoo;
	}

	public void setTitleDispOoo(String titleDispOoo) {
		this.titleDispOoo = titleDispOoo;
	}

	@Column(name = "INVITED_IN_AS", length = 1)
	public Character getInvitedInAs() {
		return this.invitedInAs;
	}

	public void setInvitedInAs(Character invitedInAs) {
		this.invitedInAs = invitedInAs;
	}

	@Column(name = "INVITED_IN_AS_START_DATE")
	public Date getInvitedInAsStartDate() {
		return this.invitedInAsStartDate;
	}

	public void setInvitedInAsStartDate(Date invitedInAsStartDate) {
		this.invitedInAsStartDate = invitedInAsStartDate;
	}

	@Column(name = "INVITED_IN_AS_END_DATE")
	public Date getInvitedInAsEndDate() {
		return this.invitedInAsEndDate;
	}

	public void setInvitedInAsEndDate(Date invitedInAsEndDate) {
		this.invitedInAsEndDate = invitedInAsEndDate;
	}

	@Column(name = "DISP_FOR_OO_ORDERING", length = 1)
	public Character getDispForOoOrdering() {
		return this.dispForOoOrdering;
	}

	public void setDispForOoOrdering(Character dispForOoOrdering) {
		this.dispForOoOrdering = dispForOoOrdering;
	}

	@Column(name = "PROD_TRK_PATH", length = 1)
	public Character getProdTrkPath() {
		return this.prodTrkPath;
	}

	public void setProdTrkPath(Character prodTrkPath) {
		this.prodTrkPath = prodTrkPath;
	}

	@Column(name = "COAUTH_CAN_HAVE_FREE_ACC_ART", length = 1)
	public Character getCoauthCanHaveFreeAccArt() {
		return this.coauthCanHaveFreeAccArt;
	}

	public void setCoauthCanHaveFreeAccArt(Character coauthCanHaveFreeAccArt) {
		this.coauthCanHaveFreeAccArt = coauthCanHaveFreeAccArt;
	}

	@Column(name = "COAUTH_CAN_SEE_ART_PROD_STAGES", length = 1)
	public Character getCoauthCanSeeArtProdStages() {
		return this.coauthCanSeeArtProdStages;
	}

	public void setCoauthCanSeeArtProdStages(Character coauthCanSeeArtProdStages) {
		this.coauthCanSeeArtProdStages = coauthCanSeeArtProdStages;
	}

	@Column(name = "COAUTH_CAN_SUBST_REVIEW_PROOFS", length = 1)
	public Character getCoauthCanSubstReviewProofs() {
		return this.coauthCanSubstReviewProofs;
	}

	public void setCoauthCanSubstReviewProofs(
			Character coauthCanSubstReviewProofs) {
		this.coauthCanSubstReviewProofs = coauthCanSubstReviewProofs;
	}

	@Column(name = "COLLG_CAN_VIEW_ART_WOL", length = 1)
	public Character getCollgCanViewArtWol() {
		return this.collgCanViewArtWol;
	}

	public void setCollgCanViewArtWol(Character collgCanViewArtWol) {
		this.collgCanViewArtWol = collgCanViewArtWol;
	}

	@Column(name = "ART_CAN_HAVE_PRD_NONPRD_PATH", length = 1)
	public Character getArtCanHavePrdNonprdPath() {
		return this.artCanHavePrdNonprdPath;
	}

	public void setArtCanHavePrdNonprdPath(Character artCanHavePrdNonprdPath) {
		this.artCanHavePrdNonprdPath = artCanHavePrdNonprdPath;
	}

	@Column(name = "CAN_COLL_CONFL_INTRST_FORMS", length = 1)
	public Character getCanCollConflIntrstForms() {
		return this.canCollConflIntrstForms;
	}

	public void setCanCollConflIntrstForms(Character canCollConflIntrstForms) {
		this.canCollConflIntrstForms = canCollConflIntrstForms;
	}

	@Column(name = "COAUTH_RECEIVE_INVITATION", length = 1)
	public Character getCoauthReceiveInvitation() {
		return this.coauthReceiveInvitation;
	}

	public void setCoauthReceiveInvitation(Character coauthReceiveInvitation) {
		this.coauthReceiveInvitation = coauthReceiveInvitation;
	}

	@Column(name = "DISP_OR_EMAIL_MRKTNG_TO_USR", length = 1)
	public Character getDispOrEmailMrktngToUsr() {
		return this.dispOrEmailMrktngToUsr;
	}

	public void setDispOrEmailMrktngToUsr(Character dispOrEmailMrktngToUsr) {
		this.dispOrEmailMrktngToUsr = dispOrEmailMrktngToUsr;
	}

	@Column(name = "DISP_OR_EMAIL_SURVEY_TO_USR", length = 1)
	public Character getDispOrEmailSurveyToUsr() {
		return this.dispOrEmailSurveyToUsr;
	}

	public void setDispOrEmailSurveyToUsr(Character dispOrEmailSurveyToUsr) {
		this.dispOrEmailSurveyToUsr = dispOrEmailSurveyToUsr;
	}

	@Column(name = "CORRAU_CAN_HAVE_FREE_ACC_ART", length = 1)
	public Character getCorrauCanHaveFreeAccArt() {
		return this.corrauCanHaveFreeAccArt;
	}

	public void setCorrauCanHaveFreeAccArt(Character corrauCanHaveFreeAccArt) {
		this.corrauCanHaveFreeAccArt = corrauCanHaveFreeAccArt;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "UPDATED_DATE")
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}