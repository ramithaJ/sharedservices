package com.wiley.gr.ace.sharedservices.persistence.entity;
// default package
// Generated Jun 23, 2015 4:17:36 PM by Hibernate Tools 3.4.0.CR1

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UserProfile generated by hbm2java
 */
@Entity
@Table(name = "USER_PROFILE")
public class UserProfile implements java.io.Serializable {

    private Integer userId;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private Users usersByMergerAccntId;
    private Users usersByUserId;
    private UserSecondaryEmailAddr userSecondaryEmailAddr;
    private String titleCd;
    private String middleName;
    private String suffixCd;
    private String alternativeName;
    private String industryCd;
    private String jobCategoryCd;
    private String optInPromoteFlg;
    private Blob profilePic;
    private Character isAccountActive;
    private Character isAccountVerified;
    private Character cdmUpdstatus;
    private Character profileVisibleFlg;
    private Date createdDate;
    private Date updatedDate;
    private Set<AuthCoauthDetails> authCoauthDetailsesForAuthorUserId = new HashSet<AuthCoauthDetails>(
            0);
    private Set<UserAreaOfInterest> userAreaOfInterests = new HashSet<UserAreaOfInterest>(
            0);
    private Set<UserAffiliations> userAffiliationses = new HashSet<UserAffiliations>(
            0);
    private Set<UserAlerts> userAlertses = new HashSet<UserAlerts>(0);
    private Set<UserSocietyDetails> userSocietyDetailses = new HashSet<UserSocietyDetails>(
            0);
    private Set<AuthColleagueDetails> authColleagueDetailsesForInvitedAuthorId = new HashSet<AuthColleagueDetails>(
            0);
    private Set<AuthCoauthDetails> authCoauthDetailsesForCoauthUserId = new HashSet<AuthCoauthDetails>(
            0);
    private Set<AuthColleagueDetails> authColleagueDetailsesForColleagueUserId = new HashSet<AuthColleagueDetails>(
            0);
    private Set<UserWoaAccounts> userWoaAccountses = new HashSet<UserWoaAccounts>(
            0);
    private Set<UserFunders> userFunderses = new HashSet<UserFunders>(0);
    private Set<UserProfileAttribVisible> userProfileAttribVisibles = new HashSet<UserProfileAttribVisible>(
            0);
    private Set<UserPreferredJournals> userPreferredJournalses = new HashSet<UserPreferredJournals>(
            0);

    public UserProfile() {
    }

    public UserProfile(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    public UserProfile(Users usersByCreatedBy, Users usersByUpdatedBy,
                       Users usersByMergerAccntId, Users usersByUserId,
                       UserSecondaryEmailAddr userSecondaryEmailAddr, String titleCd,
                       String middleName, String suffixCd, String alternativeName,
                       String industryCd, String jobCategoryCd, String optInPromoteFlg,
                       Blob profilePic, Character isAccountActive,
                       Character isAccountVerified, Character cdmUpdstatus,
                       Character profileVisibleFlg, Date createdDate, Date updatedDate,
                       Set<AuthCoauthDetails> authCoauthDetailsesForAuthorUserId,
                       Set<UserAreaOfInterest> userAreaOfInterests,
                       Set<UserAffiliations> userAffiliationses,
                       Set<UserAlerts> userAlertses,
                       Set<UserSocietyDetails> userSocietyDetailses,
                       Set<AuthColleagueDetails> authColleagueDetailsesForInvitedAuthorId,
                       Set<AuthCoauthDetails> authCoauthDetailsesForCoauthUserId,
                       Set<AuthColleagueDetails> authColleagueDetailsesForColleagueUserId,
                       Set<UserWoaAccounts> userWoaAccountses,
                       Set<UserFunders> userFunderses,
                       Set<UserProfileAttribVisible> userProfileAttribVisibles,
                       Set<UserPreferredJournals> userPreferredJournalses) {
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.usersByMergerAccntId = usersByMergerAccntId;
        this.usersByUserId = usersByUserId;
        this.userSecondaryEmailAddr = userSecondaryEmailAddr;
        this.titleCd = titleCd;
        this.middleName = middleName;
        this.suffixCd = suffixCd;
        this.alternativeName = alternativeName;
        this.industryCd = industryCd;
        this.jobCategoryCd = jobCategoryCd;
        this.optInPromoteFlg = optInPromoteFlg;
        this.profilePic = profilePic;
        this.isAccountActive = isAccountActive;
        this.isAccountVerified = isAccountVerified;
        this.cdmUpdstatus = cdmUpdstatus;
        this.profileVisibleFlg = profileVisibleFlg;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.authCoauthDetailsesForAuthorUserId = authCoauthDetailsesForAuthorUserId;
        this.userAreaOfInterests = userAreaOfInterests;
        this.userAffiliationses = userAffiliationses;
        this.userAlertses = userAlertses;
        this.userSocietyDetailses = userSocietyDetailses;
        this.authColleagueDetailsesForInvitedAuthorId = authColleagueDetailsesForInvitedAuthorId;
        this.authCoauthDetailsesForCoauthUserId = authCoauthDetailsesForCoauthUserId;
        this.authColleagueDetailsesForColleagueUserId = authColleagueDetailsesForColleagueUserId;
        this.userWoaAccountses = userWoaAccountses;
        this.userFunderses = userFunderses;
        this.userProfileAttribVisibles = userProfileAttribVisibles;
        this.userPreferredJournalses = userPreferredJournalses;
    }

    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "usersByUserId"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERGER_ACCNT_ID")
    public Users getUsersByMergerAccntId() {
        return this.usersByMergerAccntId;
    }

    public void setUsersByMergerAccntId(Users usersByMergerAccntId) {
        this.usersByMergerAccntId = usersByMergerAccntId;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Users getUsersByUserId() {
        return this.usersByUserId;
    }

    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALERT_PREF_EMAILID")
    @Cascade({CascadeType.SAVE_UPDATE})
    public UserSecondaryEmailAddr getUserSecondaryEmailAddr() {
        return this.userSecondaryEmailAddr;
    }

    public void setUserSecondaryEmailAddr(
            UserSecondaryEmailAddr userSecondaryEmailAddr) {
        this.userSecondaryEmailAddr = userSecondaryEmailAddr;
    }

    @Column(name = "TITLE_CD", length = 50)
    public String getTitleCd() {
        return this.titleCd;
    }

    public void setTitleCd(String titleCd) {
        this.titleCd = titleCd;
    }

    @Column(name = "MIDDLE_NAME", length = 50)
    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "SUFFIX_CD", length = 50)
    public String getSuffixCd() {
        return this.suffixCd;
    }

    public void setSuffixCd(String suffixCd) {
        this.suffixCd = suffixCd;
    }

    @Column(name = "ALTERNATIVE_NAME", length = 100)
    public String getAlternativeName() {
        return this.alternativeName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    @Column(name = "INDUSTRY_CD", length = 6)
    public String getIndustryCd() {
        return this.industryCd;
    }

    public void setIndustryCd(String industryCd) {
        this.industryCd = industryCd;
    }

    @Column(name = "JOB_CATEGORY_CD", length = 7)
    public String getJobCategoryCd() {
        return this.jobCategoryCd;
    }

    public void setJobCategoryCd(String jobCategoryCd) {
        this.jobCategoryCd = jobCategoryCd;
    }

    @Column(name = "OPT_IN_PROMOTE_FLG", length = 5)
    public String getOptInPromoteFlg() {
        return this.optInPromoteFlg;
    }

    public void setOptInPromoteFlg(String optInPromoteFlg) {
        this.optInPromoteFlg = optInPromoteFlg;
    }

    @Column(name = "PROFILE_PIC")
    public Blob getProfilePic() {
        return this.profilePic;
    }

    public void setProfilePic(Blob profilePic) {
        this.profilePic = profilePic;
    }

    @Column(name = "IS_ACCOUNT_ACTIVE", length = 1)
    public Character getIsAccountActive() {
        return this.isAccountActive;
    }

    public void setIsAccountActive(Character isAccountActive) {
        this.isAccountActive = isAccountActive;
    }

    @Column(name = "IS_ACCOUNT_VERIFIED", length = 1)
    public Character getIsAccountVerified() {
        return this.isAccountVerified;
    }

    public void setIsAccountVerified(Character isAccountVerified) {
        this.isAccountVerified = isAccountVerified;
    }

    @Column(name = "CDM_UPDSTATUS", length = 1)
    public Character getCdmUpdstatus() {
        return this.cdmUpdstatus;
    }

    public void setCdmUpdstatus(Character cdmUpdstatus) {
        this.cdmUpdstatus = cdmUpdstatus;
    }

    @Column(name = "PROFILE_VISIBLE_FLG", length = 1)
    public Character getProfileVisibleFlg() {
        return this.profileVisibleFlg;
    }

    public void setProfileVisibleFlg(Character profileVisibleFlg) {
        this.profileVisibleFlg = profileVisibleFlg;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileByAuthorUserId")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<AuthCoauthDetails> getAuthCoauthDetailsesForAuthorUserId() {
        return this.authCoauthDetailsesForAuthorUserId;
    }

    public void setAuthCoauthDetailsesForAuthorUserId(
            Set<AuthCoauthDetails> authCoauthDetailsesForAuthorUserId) {
        this.authCoauthDetailsesForAuthorUserId = authCoauthDetailsesForAuthorUserId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<UserAreaOfInterest> getUserAreaOfInterests() {
        return this.userAreaOfInterests;
    }

    public void setUserAreaOfInterests(
            Set<UserAreaOfInterest> userAreaOfInterests) {
        this.userAreaOfInterests = userAreaOfInterests;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<UserAffiliations> getUserAffiliationses() {
        return this.userAffiliationses;
    }

    public void setUserAffiliationses(Set<UserAffiliations> userAffiliationses) {
        this.userAffiliationses = userAffiliationses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<UserAlerts> getUserAlertses() {
        return this.userAlertses;
    }

    public void setUserAlertses(Set<UserAlerts> userAlertses) {
        this.userAlertses = userAlertses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<UserSocietyDetails> getUserSocietyDetailses() {
        return this.userSocietyDetailses;
    }

    public void setUserSocietyDetailses(
            Set<UserSocietyDetails> userSocietyDetailses) {
        this.userSocietyDetailses = userSocietyDetailses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileByInvitedAuthorId")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<AuthColleagueDetails> getAuthColleagueDetailsesForInvitedAuthorId() {
        return this.authColleagueDetailsesForInvitedAuthorId;
    }

    public void setAuthColleagueDetailsesForInvitedAuthorId(
            Set<AuthColleagueDetails> authColleagueDetailsesForInvitedAuthorId) {
        this.authColleagueDetailsesForInvitedAuthorId = authColleagueDetailsesForInvitedAuthorId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileByCoauthUserId")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<AuthCoauthDetails> getAuthCoauthDetailsesForCoauthUserId() {
        return this.authCoauthDetailsesForCoauthUserId;
    }

    public void setAuthCoauthDetailsesForCoauthUserId(
            Set<AuthCoauthDetails> authCoauthDetailsesForCoauthUserId) {
        this.authCoauthDetailsesForCoauthUserId = authCoauthDetailsesForCoauthUserId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfileByColleagueUserId")
    public Set<AuthColleagueDetails> getAuthColleagueDetailsesForColleagueUserId() {
        return this.authColleagueDetailsesForColleagueUserId;
    }

    public void setAuthColleagueDetailsesForColleagueUserId(
            Set<AuthColleagueDetails> authColleagueDetailsesForColleagueUserId) {
        this.authColleagueDetailsesForColleagueUserId = authColleagueDetailsesForColleagueUserId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    public Set<UserWoaAccounts> getUserWoaAccountses() {
        return this.userWoaAccountses;
    }

    public void setUserWoaAccountses(Set<UserWoaAccounts> userWoaAccountses) {
        this.userWoaAccountses = userWoaAccountses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<UserFunders> getUserFunderses() {
        return this.userFunderses;
    }

    public void setUserFunderses(Set<UserFunders> userFunderses) {
        this.userFunderses = userFunderses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    public Set<UserProfileAttribVisible> getUserProfileAttribVisibles() {
        return this.userProfileAttribVisibles;
    }

    public void setUserProfileAttribVisibles(
            Set<UserProfileAttribVisible> userProfileAttribVisibles) {
        this.userProfileAttribVisibles = userProfileAttribVisibles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    @Cascade({CascadeType.SAVE_UPDATE})
    public Set<UserPreferredJournals> getUserPreferredJournalses() {
        return this.userPreferredJournalses;
    }

    public void setUserPreferredJournalses(
            Set<UserPreferredJournals> userPreferredJournalses) {
        this.userPreferredJournalses = userPreferredJournalses;
    }

}
