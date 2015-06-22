package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * UserPreferredJournals generated by hbm2java
 */
@Entity
@Table(name = "USER_PREFERRED_JOURNALS")
public class UserPreferredJournals implements java.io.Serializable {

    private UserPreferredJournalsId id;
    private Journals journals;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private AuthorProfile authorProfile;

    public UserPreferredJournals() {
    }

    public UserPreferredJournals(UserPreferredJournalsId id) {
        this.id = id;
    }

    public UserPreferredJournals(UserPreferredJournalsId id, Journals journals,
                                 Users usersByCreatedBy, Users usersByUpdatedBy,
                                 AuthorProfile authorProfile) {
        this.id = id;
        this.journals = journals;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.authorProfile = authorProfile;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "USER_ID", precision = 22, scale = 0)),
            @AttributeOverride(name = "journalId", column = @Column(name = "JOURNAL_ID", precision = 22, scale = 0)),
            @AttributeOverride(name = "createdDate", column = @Column(name = "CREATED_DATE")),
            @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY", precision = 22, scale = 0)),
            @AttributeOverride(name = "updatedDate", column = @Column(name = "UPDATED_DATE")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "UPDATED_BY", precision = 22, scale = 0))})
    public UserPreferredJournalsId getId() {
        return this.id;
    }

    public void setId(UserPreferredJournalsId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOURNAL_ID", insertable = false, updatable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    public Journals getJournals() {
        return this.journals;
    }

    public void setJournals(Journals journals) {
        this.journals = journals;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", insertable = false, updatable = false)
    public Users getUsersByCreatedBy() {
        return this.usersByCreatedBy;
    }

    public void setUsersByCreatedBy(Users usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", insertable = false, updatable = false)
    public Users getUsersByUpdatedBy() {
        return this.usersByUpdatedBy;
    }

    public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
        this.usersByUpdatedBy = usersByUpdatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    public AuthorProfile getAuthorProfile() {
        return this.authorProfile;
    }

    public void setAuthorProfile(AuthorProfile authorProfile) {
        this.authorProfile = authorProfile;
    }

}