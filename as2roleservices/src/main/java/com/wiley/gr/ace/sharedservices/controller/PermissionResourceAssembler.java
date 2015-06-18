package com.wiley.gr.ace.sharedservices.controller;

import com.wiley.gr.ace.sharedservices.service.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissionResourceAssembler extends ResourceAssemblerSupport<Permission, PermissionResource> {
    @Autowired
    private RoleResourceAssembler roleResourceAssembler;

    public PermissionResourceAssembler() {
        super(PermissionController.class, PermissionResource.class);
    }

    @Override
    public PermissionResource toResource(Permission permission) {
        PermissionResource permissionResource = createResourceWithId(permission.getCode(), permission);
        permissionResource.setCode(permission.getCode());
        permissionResource.setName(permission.getName());
        permissionResource.setGroup(permission.getGroup());
        permissionResource.setPublishingDate(permission.getPublishingDate());
        permissionResource.setRole(roleResourceAssembler.toResource(permission.getRole()));
        return permissionResource;
    }
}
