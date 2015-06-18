package com.wiley.gr.ace.sharedservices.controller;

import com.wiley.gr.ace.sharedservices.exceptions.ResourceNotFoundException;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.service.PermissionRepository;
import com.wiley.gr.ace.sharedservices.service.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/roles")
public class RoleController extends AbstractController {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleResourceAssembler roleResourceAssembler;
    @Autowired
    private PermissionResourceAssembler permissionResourceAssembler;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> createNewRole(@RequestBody NewRole newRole) {
        Role role = permissionRepository.createNewRole(newRole.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(getClass()).getRole(role.getRoleId())).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<RoleResource> getRoles() throws SharedServiceException {
        return roleResourceAssembler.toResources(permissionRepository.findRoles());
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public RoleResource getRole(@PathVariable("roleId") int authorId) {
        try {
            return roleResourceAssembler.toResource(findRoleAndValidate(authorId));
        } catch (SharedServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.GET)
    @ResponseBody
    public List<PermissionResource> getRolePermissions(@PathVariable("roleId") int roleId) {
        try {
            return permissionResourceAssembler.toResources(findRoleAndValidate(roleId).getPermissions());
        } catch (SharedServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Role findRoleAndValidate(int authorId) throws SharedServiceException {
        Role role = permissionRepository.findRole(authorId);
        if (role == null) {
            throw new ResourceNotFoundException("Unable to find role with id=" + authorId);
        }
        return role;
    }

}
