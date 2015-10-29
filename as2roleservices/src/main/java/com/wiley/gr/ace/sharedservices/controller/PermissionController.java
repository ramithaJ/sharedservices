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

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.model.StatusPayload;
import com.wiley.gr.ace.sharedservices.model.StatusPayload.Status;
import com.wiley.gr.ace.sharedservices.service.AdditionalPermission;
import com.wiley.gr.ace.sharedservices.service.Permission;
import com.wiley.gr.ace.sharedservices.service.PermissionRepository;

/**
 * The Class PermissionController.
 */
@Controller
@RequestMapping("/v1/permissions")
public class PermissionController extends AbstractController {

    /** The Constant LOGGER. */
    public static final Logger LOGGER = LoggerFactory
            .getLogger(PermissionController.class);

    /** The permission repository. */
    @Autowired
    private PermissionRepository permissionRepository;

    /** The permission resource assembler. */
    @Autowired
    private PermissionResourceAssembler permissionResourceAssembler;

    /**
     * Gets the permissions.
     *
     * @param permissionGroupCd
     *            the permission group cd
     * @return the permissions
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<PermissionResource> getPermissions(
            @RequestParam(value = "group", required = false) final String permissionGroupCd) {
        try {
            if (permissionGroupCd != null) {
                return permissionResourceAssembler
                        .toResources(permissionRepository
                                .findPermissions(permissionGroupCd));
            } else {
                return permissionResourceAssembler
                        .toResources(permissionRepository.findPermissions());
            }
        } catch (final SharedServiceException e) {
            PermissionController.LOGGER.error("get cannot be performed", e);
        }
        return new ArrayList<>();

    }

    /**
     * Creates the new permission.
     *
     * @param permission
     *            the permission
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public StatusPayload createNewPermission(@RequestBody Permission permission) {
        try {
            permissionRepository.createNewPermission(permission);
            return new StatusPayload();
        } catch (final SharedServiceException e) {
            // TODO Auto-generated catch block
            PermissionController.LOGGER.error("create cannot be performed", e);
            return new StatusPayload(Status.FAILURE, e.getMessage());
        }
    }

    /**
     * Gets the permission.
     *
     * @param permissionCd
     *            the permission cd
     * @return the permission
     */
    @RequestMapping(value = "/{permissionCd}", method = RequestMethod.GET)
    @ResponseBody
    public PermissionResource getPermission(
            @PathVariable("permissionCd") String permissionCd) {
        try {
            return permissionResourceAssembler.toResource(permissionRepository
                    .findPermission(permissionCd));
        } catch (final SharedServiceException e) {
            PermissionController.LOGGER.error("get cannot be performed", e);
        }

        return null;
    }

    /**
     * Gets the permission of user.
     *
     * @param objectId
     *            the object id
     * @param userId
     *            the user id
     * @return the permission of user
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<PermissionResource> getPermissionOfUser(
            @RequestParam("objectId") int objectId,
            @RequestParam("userId") int userId) {
        try {
            return permissionResourceAssembler.toResources(permissionRepository
                    .findAdditionalPermissions(userId, objectId));
        } catch (final SharedServiceException e) {
            PermissionController.LOGGER.error("get cannot be performed", e);
        }

        return new ArrayList<>();
    }

    /**
     * Gets the permission of user.
     *
     * @param objectId
     *            the object id
     * @param userId
     *            the user id
     * @param additionalPermission
     *            the additional permission
     * @return the permission of user
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public StatusPayload updatePermissionOfUser(
            @RequestParam("objectId") int objectId,
            @RequestParam("userId") int userId,
            @RequestBody AdditionalPermission additionalPermission) {
        try {
            permissionRepository.updateAdditionalPermissions(userId, objectId,
                    additionalPermission);
            return new StatusPayload();
        } catch (final SharedServiceException e) {
            PermissionController.LOGGER.error("update cannot be performed", e);
            return new StatusPayload(Status.FAILURE, e.getMessage());
        }
    }

    /**
     * Delete permissions of user.
     *
     * @param objectId
     *            the object id
     * @param userId
     *            the user id
     * @return true, if successful
     */
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    @ResponseBody
    public StatusPayload deletePermissionsOfUser(
            @RequestParam("objectId") int objectId,
            @RequestParam("userId") int userId) {
        try {
            permissionRepository.deletePermissionOfUser(userId, objectId);
            return new StatusPayload();
        } catch (final SharedServiceException e) {
            PermissionController.LOGGER.error("delete cannot be performed", e);
            return new StatusPayload(Status.FAILURE, e.getMessage());
        }
    }

}
