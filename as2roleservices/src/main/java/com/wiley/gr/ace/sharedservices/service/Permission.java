package com.wiley.gr.ace.sharedservices.service;

import java.util.Date;

public class Permission {
    private final String code;
    private final String name;
    private final Role role;
    private final String group;
    private final Date publishingDate;

    public Permission(Role role, String code, String name, String group, Date publishingDate) {
        this.code = code;
        this.name = name;
        this.role = role;
        this.group = group;
        this.publishingDate = publishingDate;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getGroup() {
        return group;
    }

    public Date getPublishingDate() {
        return publishingDate;
    }
}
