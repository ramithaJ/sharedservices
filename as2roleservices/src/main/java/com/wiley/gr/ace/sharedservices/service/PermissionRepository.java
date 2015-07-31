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
     * @param role
     *            the role
     * @return the role
     * @throws SharedServiceException
     *             the shared service exception
     */
    void createNewRole(Role role) throws SharedServiceException;

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
     * @param permissionCd
     *            the permission cd
     * @return the list
     * @throws SharedServiceException
     *             the shared service exception
     */
    Permission findPermission(String permissionCd)
            throws SharedServiceException;

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
    Role findRoleByUser(int userId) throws SharedServiceException;

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
     * Delete permission of a role.
     *
     * @param roleId
     * @param permissionCd
     * @throws SharedServiceException
     */
    void deletePermissionOfRole(int roleId, String permissionCd)
            throws SharedServiceException;

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

    /**
     * Update permission.
     *
     * @param permissionList
     *            the permission list
     * @param roleId
     *            the role id
     * @throws SharedServiceException
     *             the shared service exception
     */
    void updatePermission(List<Permission> permissionList, int roleId)
            throws SharedServiceException;

    /**
     * Update additional permissions.
     *
     * @param userId
     *            the user id
     * @param articleId
     *            the article id
     * @param additionalPermission
     *            the additional permission
     * @throws SharedServiceException
     *             the shared service exception
     */
    void updateAdditionalPermissions(int userId, int articleId,
            AdditionalPermission additionalPermission)
            throws SharedServiceException;

    /**
     * Creates the new permission.
     *
     * @param permission
     *            the permission
     * @throws SharedServiceException
     *             the shared service exception
     */
    void createNewPermission(Permission permission)
            throws SharedServiceException;

    /**
     * Find permissions.
     *
     * @return List of roles
     * @throws SharedServiceException
     *             the shared service exception
     */
    List<Permission> findPermissions() throws SharedServiceException;
}