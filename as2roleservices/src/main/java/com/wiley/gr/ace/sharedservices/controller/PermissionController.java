package com.wiley.gr.ace.sharedservices.controller;

import com.wiley.gr.ace.sharedservices.exceptions.ResourceNotFoundException;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.service.Permission;
import com.wiley.gr.ace.sharedservices.service.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/permissions")
public class PermissionController extends AbstractController {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionResourceAssembler permissionResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PermissionResource> getPermissions() throws SharedServiceException {
        return permissionResourceAssembler.toResources(permissionRepository.findPermissions());
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    @ResponseBody
    public PermissionResource getPermission(@PathVariable("code") String code) throws SharedServiceException {
        return permissionResourceAssembler.toResource(findPermissionAndValidate(code));
    }

    private Permission findPermissionAndValidate(String code) throws SharedServiceException {
        Permission permission = permissionRepository.findPermission(code);
        if (permission == null) {
            throw new ResourceNotFoundException("Unable to find permission with ISBN=" + code);
        }
        return permission;
    }
}
