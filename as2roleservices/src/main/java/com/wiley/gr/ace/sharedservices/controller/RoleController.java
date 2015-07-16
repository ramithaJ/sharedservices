package com.wiley.gr.ace.sharedservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wiley.gr.ace.sharedservices.exceptions.ResourceNotFoundException;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.service.PermissionRepository;
import com.wiley.gr.ace.sharedservices.service.Role;

/**
 * The Class RoleController.
 */
@Controller
@RequestMapping("/roles")
public class RoleController extends AbstractController {

    /** The permission repository. */
    @Autowired
    private PermissionRepository permissionRepository;

    /** The role resource assembler. */
    @Autowired
    private RoleResourceAssembler roleResourceAssembler;

    /** The permission resource assembler. */
    @Autowired
    private PermissionResourceAssembler permissionResourceAssembler;

    /**
     * Creates the new role.
     *
     * @param role
     *            the role
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void createNewRole(@RequestBody Role role) {
        try {
            permissionRepository.createNewRole(role);
        } catch (final SharedServiceException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets the roles.
     *
     * @return the roles
     * @throws SharedServiceException
     *             the shared service exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<RoleResource> getRoles() throws SharedServiceException {
        return roleResourceAssembler.toResources(permissionRepository.findRoles());
    }

    /**
     * Gets the role.
     *
     * @param roleId
     *            the role id
     * @return the role
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public RoleResource getRole(@PathVariable("roleId") int roleId) {
        try {
            return roleResourceAssembler.toResource(permissionRepository.findRole(roleId));
        } catch (final SharedServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update role id.
     *
     * @param roleId
     *            the role id
     * @param role
     *            the role
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT)
    public void updateRoleId(@PathVariable("roleId") int roleId,
            @RequestBody Role role) {
        try {
            permissionRepository.updateRole(roleId, role);
        } catch (final SharedServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete role id.
     *
     * @param roleId
     *            the role id
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public void deleteRoleId(@PathVariable("roleId") int roleId) {
        try {
            permissionRepository.deleteRole(roleId);
        } catch (final SharedServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the roles of user.
     *
     * @param userId
     *            the user id
     * @return the roles of user
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public RoleResource getRolesOfUser(@RequestParam("userId") int userId) {
        try {
            return roleResourceAssembler.toResource(permissionRepository.findRoleByUser(userId));
        } catch (final SharedServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the role permissions.
     *
     * @param roleId
     *            the role id
     * @return the role permissions
     */
    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.GET)
    @ResponseBody
    public List<PermissionResource> getRolePermissions(
            @PathVariable("roleId") int roleId) {
        try {
            return permissionResourceAssembler.toResources(findRoleAndValidate(
                    roleId).getPermissions());
        } catch (final SharedServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Find role and validate.
     *
     * @param roleId
     *            the role id
     * @return the role
     * @throws SharedServiceException
     *             the shared service exception
     */
    private Role findRoleAndValidate(int roleId) throws SharedServiceException {
        final Role role = permissionRepository.findRole(roleId);
        if (role == null) {
            throw new ResourceNotFoundException("Unable to find role with id="
                    + roleId);
        }
        return role;
    }

}
