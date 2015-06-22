package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;
import java.util.Date;

/**
 * AuthColleagueDetails generated by hbm2java
 */
@Entity
@Table(name = "AUTH_COLLEAGUE_DETAILS")
public class AuthColleagueDetails implements java.io.Serializable {

    private Integer authColleagueId;
    private AuthorProfile authorProfileByColleagueUserId;
    private Users usersByCreatedBy;
    private AuthorProfile authorProfileByInvitedAuthorId;
    private Users usersByUpdatedBy;
    private String firstName;
    private String lastName;
    private String emailAddr;
    private Integer assignedArticleId;
    private Date createdDate;
    private Date updatedDate;

    public AuthColleagueDetails() {
    }

    public AuthColleagueDetails(Integer authColleagueId) {
        this.authColleagueId = authColleagueId;
    }

    public AuthColleagueDetails(Integer authColleagueId,
                                AuthorProfile authorProfileByColleagueUserId,
                                Users usersByCreatedBy,
                                AuthorProfile authorProfileByInvitedAuthorId,
                                Users usersByUpdatedBy, String firstName, String lastName,
                                String emailAddr, Integer assignedArticleId, Date createdDate,
                                Date updatedDate) {
        this.authColleagueId = authColleagueId;
        this.authorProfileByColleagueUserId = authorProfileByColleagueUserId;
        this.usersByCreatedBy = usersByCreatedBy;
        this.authorProfileByInvitedAuthorId = authorProfileByInvitedAuthorId;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddr = emailAddr;
        this.assignedArticleId = assignedArticleId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Id
    @Column(name = "AUTH_COLLEAGUE_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getAuthColleagueId() {
        return this.authColleagueId;
    }

    public void setAuthColleagueId(Integer authColleagueId) {
        this.authColleagueId = authColleagueId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLEAGUE_USER_ID")
    public AuthorProfile getAuthorProfileByColleagueUserId() {
        return this.authorProfileByColleagueUserId;
    }

    public void setAuthorProfileByColleagueUserId(
            AuthorProfile authorProfileByColleagueUserId) {
        this.authorProfileByColleagueUserId = authorProfileByColleagueUserId;
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
    @JoinColumn(name = "INVITED_AUTHOR_ID")
    public AuthorProfile getAuthorProfileByInvitedAuthorId() {
        return this.authorProfileByInvitedAuthorId;
    }

    public void setAuthorProfileByInvitedAuthorId(
            AuthorProfile authorProfileByInvitedAuthorId) {
        this.authorProfileByInvitedAuthorId = authorProfileByInvitedAuthorId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    public Users getUsersByUpdatedBy() {
        return this.usersByUpdatedBy;
    }

    public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
        this.usersByUpdatedBy = usersByUpdatedBy;
    }

    @Column(name = "FIRST_NAME", length = 50)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME", length = 50)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "EMAIL_ADDR", length = 250)
    public String getEmailAddr() {
        return this.emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    @Column(name = "ASSIGNED_ARTICLE_ID", precision = 22, scale = 0)
    public Integer getAssignedArticleId() {
        return this.assignedArticleId;
    }

    public void setAssignedArticleId(Integer assignedArticleId) {
        this.assignedArticleId = assignedArticleId;
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