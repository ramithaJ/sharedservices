package com.wiley.gr.ace.sharedservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.service.Permission;
import com.wiley.gr.ace.sharedservices.service.PermissionRepository;

/**
 * The Class PermissionController.
 */
@Controller
@RequestMapping("/permissions")
public class PermissionController extends AbstractController {

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
     * @throws SharedServiceException
     *             the shared service exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Permission> getPermissions(
            @RequestParam("group") String permissionGroupCd)
            throws SharedServiceException {
        return permissionRepository.findPermissions(permissionGroupCd);
    }

    /**
     * Gets the permission.
     *
     * @param roleId
     *            the role id
     * @return the permission
     * @throws SharedServiceException
     *             the shared service exception
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Permission> getPermission(@PathVariable("roleId") int roleId)
            throws SharedServiceException {
        return permissionRepository.findPermission(roleId);
    }

    /**
     * Delete permissions.
     *
     * @param roleId
     *            the role id
     * @throws SharedServiceException
     *             the shared service exception
     */
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public void deletePermissions(@PathVariable("roleId") int roleId)
            throws SharedServiceException {
        permissionRepository.deletePermission(roleId);
    }

    /**
     * Gets the permission of user.
     *
     * @param articleId
     *            the article id
     * @param userId
     *            the user id
     * @return the permission of user
     * @throws SharedServiceException
     *             the shared service exception
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<Permission> getPermissionOfUser(
            @RequestParam("articleId") int articleId,
            @RequestParam("userId") int userId) throws SharedServiceException {
        return permissionRepository
                .findAdditionalPermissions(userId, articleId);
    }

    /**
     * Delete permissions of user.
     *
     * @param articleId
     *            the article id
     * @param userId
     *            the user id
     * @throws SharedServiceException
     *             the shared service exception
     */
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void deletePermissionsOfUser(
            @RequestParam("articleId") int articleId,
            @RequestParam("userId") int userId) throws SharedServiceException {
        permissionRepository.deletePermissionOfUser(userId, articleId);
    }

}
