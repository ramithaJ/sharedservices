package com.wiley.gr.ace.sharedservices.controller;

import org.springframework.hateoas.ResourceSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PermissionResource extends ResourceSupport {
    private String code;
    private String name;
    private String group;
    private String publishingDate;
    private RoleResource role;

    public String getCode() {
        return code;
    }

    public void setCode(String isbn) {
        this.code = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String publisher) {
        this.group = publisher;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = new SimpleDateFormat("MMM dd, yyyy").format(publishingDate);
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public RoleResource getRole() {
        return role;
    }

    public void setRole(RoleResource role) {
        this.role = role;
    }
}
