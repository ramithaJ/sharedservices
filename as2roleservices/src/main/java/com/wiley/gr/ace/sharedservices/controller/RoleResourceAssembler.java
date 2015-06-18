package com.wiley.gr.ace.sharedservices.controller;

import com.wiley.gr.ace.sharedservices.service.Role;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RoleResourceAssembler extends ResourceAssemblerSupport<Role, RoleResource> {
    public RoleResourceAssembler() {
        super(RoleController.class, RoleResource.class);
    }

    @Override
    public RoleResource toResource(Role role) {
        RoleResource resource = createResourceWithId(role.getRoleId(), role);
        resource.add(linkTo(methodOn(RoleController.class).getRolePermissions(role.getRoleId())).withRel("permissions"));
        return resource;
    }

    @Override
    protected RoleResource instantiateResource(Role role) {
        return new RoleResource(role.getRoleId(), role.getName());
    }
}
