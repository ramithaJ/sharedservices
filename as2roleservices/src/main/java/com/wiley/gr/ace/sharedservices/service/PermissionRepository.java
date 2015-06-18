package com.wiley.gr.ace.sharedservices.service;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;

import java.util.List;

public interface PermissionRepository {
    Role findRole(int authorId) throws SharedServiceException;

    List<Role> findRoles() throws SharedServiceException;

    Role createNewRole(String name);

    Permission findPermission(String isbn) throws SharedServiceException;

    List<Permission> findPermissions() throws SharedServiceException;
}