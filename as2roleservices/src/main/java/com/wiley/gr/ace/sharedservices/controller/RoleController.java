package com.wiley.gr.ace.sharedservices.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.wiley.gr.ace.sharedservices.model.StatusPayload;
import com.wiley.gr.ace.sharedservices.model.StatusPayload.Status;
import com.wiley.gr.ace.sharedservices.service.Permission;
import com.wiley.gr.ace.sharedservices.service.PermissionRepository;
import com.wiley.gr.ace.sharedservices.service.Role;

/**
 * The Class RoleController.
 */
@Controller
@RequestMapping("/roles")
public class RoleController extends AbstractController {

    /** The Constant LOGGER. */
    public static final Logger LOGGER = LoggerFactory
            .getLogger(RoleController.class);

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
    public boolean createNewRole(@RequestBody Role role) {
        try {
            permissionRepository.createNewRole(role);
            return true;
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("create cannot be performed", e);
        }
        return false;

    }

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<RoleResource> getRoles() {
        try {
            return roleResourceAssembler.toResources(permissionRepository
                    .findRoles());
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("Get cannot be performed", e);
        }

        return new ArrayList<>();
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
            return roleResourceAssembler.toResource(permissionRepository
                    .findRole(roleId));
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("Get cannot be performed", e);
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
     * @return true, if successful
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.PUT)
    @ResponseBody
    public boolean updateRoleId(@PathVariable("roleId") int roleId,
            @RequestBody Role role) {
        try {
            permissionRepository.updateRole(roleId, role);
            return true;
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("Update cannot be performed", e);
        }
        return false;
    }

    /**
     * Delete role id.
     *
     * @param roleId
     *            the role id
     * @return true, if successful
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusPayload deleteRoleId(@PathVariable("roleId") int roleId) {
        try {
            permissionRepository.deleteRole(roleId);
            return new StatusPayload();
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("Delete cannot be performed", e);
            return new StatusPayload(Status.FAILURE, e.getMessage());
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
            return roleResourceAssembler.toResource(permissionRepository
                    .findRoleByUser(userId));
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("get cannot be performed", e);
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
            RoleController.LOGGER.error("Get cannot be performed", e);
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

    /**
     * Gets the permission.
     *
     * @param permissionList
     *            the permission list
     * @param roleId
     *            the role id
     * @return the permission
     */
    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.PUT)
    @ResponseBody
    public StatusPayload updatePermission(
            @RequestBody List<Permission> permissionList,
            @PathVariable("roleId") int roleId) {

        try {
            permissionRepository.updatePermission(permissionList, roleId);
            return new StatusPayload();
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("Update cannot be performed", e);
            return new StatusPayload(Status.FAILURE, e.getMessage());
        }
    }

    /**
     * Delete permissions.
     *
     * @param roleId
     *            the role id
     * @return true, if successful
     */
    @RequestMapping(value = "/{roleId}/permissions", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusPayload deletePermissions(@PathVariable("roleId") int roleId) {
        try {
            permissionRepository.deletePermission(roleId);
            return new StatusPayload();
        } catch (final SharedServiceException e) {
            RoleController.LOGGER.error("Delete cannot be performed", e);
            return new StatusPayload(Status.FAILURE, e.getMessage());
        }

    }
}
