package com.wiley.gr.ace.sharedservices.controller;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.wiley.gr.ace.sharedservices.service.Role;

/**
 * The Class RoleResourceAssembler.
 */
@Component
public class RoleResourceAssembler extends
        ResourceAssemblerSupport<Role, RoleResource> {

    /**
     * Instantiates a new role resource assembler.
     */
    public RoleResourceAssembler() {
        super(RoleController.class, RoleResource.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.hateoas.ResourceAssembler#toResource(java.lang.Object
     * )
     */
    @Override
    public RoleResource toResource(Role role) {
        final RoleResource resource = createResourceWithId(role.getRoleId(),
                role);
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(RoleController.class)
                        .getRolePermissions(role.getRoleId())).withRel(
                "permissions"));
        return resource;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.hateoas.mvc.ResourceAssemblerSupport#instantiateResource
     * (java.lang.Object)
     */
    @Override
    protected RoleResource instantiateResource(Role role) {
        return new RoleResource(role);
    }
}
