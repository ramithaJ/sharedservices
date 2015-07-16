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
        permissionResource.setPermissionCd(permission.getPermissionCd());
        permissionResource.setPermissionName(permission.getPermissionName());
        /*permissionResource.setRole(roleResourceAssembler.toResource(permission.getRole()));*/
        /*permissionResource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(PermissionController.class)
                        .getPermissions()).withRel(
                "permissions"));*/
        return permissionResource;
    }
}
