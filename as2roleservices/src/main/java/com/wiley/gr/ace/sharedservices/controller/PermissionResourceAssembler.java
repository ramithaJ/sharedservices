package com.wiley.gr.ace.sharedservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.wiley.gr.ace.sharedservices.service.Permission;

/**
 * The Class PermissionResourceAssembler.
 */
@Component
public class PermissionResourceAssembler extends
ResourceAssemblerSupport<Permission, PermissionResource> {

    /** The role resource assembler. */
    @Autowired
    private RoleResourceAssembler roleResourceAssembler;

    /**
     * Instantiates a new permission resource assembler.
     */
    public PermissionResourceAssembler() {
        super(PermissionController.class, PermissionResource.class);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.hateoas.ResourceAssembler#toResource(java.lang.Object
     * )
     */
    @Override
    public PermissionResource toResource(Permission permission) {
        final PermissionResource permissionResource = createResourceWithId(
                permission.getPermissionCd(), permission);
        permissionResource.setCode(permission.getPermissionCd());
        permissionResource.setName(permission.getPermissionName());
        /*
         * permissionResource.setGroup(permission.getGroup());
         * permissionResource.setPublishingDate(permission.getPublishingDate());
         * permissionResource
         * .setRole(roleResourceAssembler.toResource(permission.getRole()));
         */
        return permissionResource;
    }
}
