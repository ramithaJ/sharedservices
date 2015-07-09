package com.wiley.gr.ace.sharedservices.service;

import java.util.List;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;

/**
 * The Interface PermissionRepository.
 */
public interface PermissionRepository {

    /**
     * Creates the new role.
     *
     * @param name
     *            the name
     * @return the role
     */
    Role createNewRole(String name);

    /**
     * Find role.
     *
     * @param authorId
     *            the author id
     * @return the role
     * @throws SharedServiceException
     *             the shared service exception
     */
    Role findRole(int authorId) throws SharedServiceException;

    /**
     * Find roles.
     *
     * @return the list
     * @throws SharedServiceException
     *             the shared service exception
     */
    List<Role> findRoles() throws SharedServiceException;

    /**
     * Find permission.
     *
     * @param roleId
     *            the role id
     * @return the list
     * @throws SharedServiceException
     *             the shared service exception
     */
    List<Permission> findPermission(int roleId) throws SharedServiceException;

    /**
     * Find permissions.
     *
     * @param permissionGroupCd
     *            the permission group cd
     * @return the list
     * @throws SharedServiceException
     *             the shared service exception
     */
    List<Permission> findPermissions(String permissionGroupCd)
            throws SharedServiceException;

    /**
     * Find role of user.
     *
     * @param userId
     *            the user id
     * @return the role
     * @throws SharedServiceException
     *             the shared service exception
     */
    Role findRoleOfUser(int userId) throws SharedServiceException;

    /**
     * Find additional permissions.
     *
     * @param userId
     *            the user id
     * @param objectId
     *            the object id
     * @return the list
     * @throws SharedServiceException
     *             the shared service exception
     */
    List<Permission> findAdditionalPermissions(int userId, int objectId)
            throws SharedServiceException;

    /**
     * Delete role.
     *
     * @param roleId
     *            the role id
     * @throws SharedServiceException
     *             the shared service exception
     */
    void deleteRole(int roleId) throws SharedServiceException;

    /**
     * Delete permission.
     *
     * @param roleId
     *            the role id
     * @throws SharedServiceException
     *             the shared service exception
     */
    void deletePermission(int roleId) throws SharedServiceException;

    /**
     * Delete permission of user.
     *
     * @param userId
     *            the user id
     * @param articleId
     *            the article id
     * @throws SharedServiceException
     *             the shared service exception
     */
    void deletePermissionOfUser(int userId, int articleId)
            throws SharedServiceException;

    /**
     * Update role.
     *
     * @param roleId
     *            the role id
     * @param role
     *            the role
     * @throws SharedServiceException
     *             the shared service exception
     */
    void updateRole(int roleId, Role role) throws SharedServiceException;
}