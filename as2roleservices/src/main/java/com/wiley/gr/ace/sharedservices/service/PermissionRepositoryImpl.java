package com.wiley.gr.ace.sharedservices.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.persistence.entity.AdditionalPermissions;
import com.wiley.gr.ace.sharedservices.persistence.entity.ObjectTypes;
import com.wiley.gr.ace.sharedservices.persistence.entity.PermissionGroups;
import com.wiley.gr.ace.sharedservices.persistence.entity.Permissions;
import com.wiley.gr.ace.sharedservices.persistence.entity.RolePermissions;
import com.wiley.gr.ace.sharedservices.persistence.entity.RolePermissionsId;
import com.wiley.gr.ace.sharedservices.persistence.entity.Roles;
import com.wiley.gr.ace.sharedservices.persistence.entity.UserRoles;
import com.wiley.gr.ace.sharedservices.persistence.entity.Users;

/**
 * The Class PermissionRepositoryImpl.
 */
@Component
public class PermissionRepositoryImpl implements PermissionRepository {

    /** The session factory. */
    @Autowired
    private SessionFactory sessionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#findRoles()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findRoles() throws SharedServiceException {
        Session session = null;
        final List<Role> roleList = new ArrayList<>();
        try {

            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final List<Roles> rolesList = session.createCriteria(Roles.class)
                    .list();
            if (null == rolesList || rolesList.isEmpty()) {
                return new ArrayList<>();
            }

            Permissions permissions;
            List<String> groupList;
            List<Permission> permissionList;
            Set<RolePermissions> rolePermissionsSet;

            for (final Roles role : rolesList) {
                permissionList = new ArrayList<>();
                rolePermissionsSet = role.getRolePermissionses();
                for (final RolePermissions rolePermissions : rolePermissionsSet) {
                    groupList = new ArrayList<>();
                    permissions = rolePermissions.getPermissions();
                    if (null != permissions) {
                        permissionList.add(new Permission(permissions
                                .getPermissionCd(), permissions
                                .getPermissionName(), groupList));
                        for (final PermissionGroups groupsSet : permissions
                                .getPermissionGroupses()) {
                            groupList.add(groupsSet.getId()
                                    .getPermissionGroupCd());
                        }
                    }
                }
                roleList.add(new Role(role.getRoleId(), role.getRoleName(),
                        permissionList));
            }

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

        return roleList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#findRoles()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Permission> findPermissions() throws SharedServiceException {
        Session session = null;
        final List<Permission> permissionList = new ArrayList<>();
        try {

            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final List<Permissions> permissionsList = session.createCriteria(
                    Permissions.class).list();
            if (null == permissionsList || permissionsList.isEmpty()) {
                return permissionList;
            }

            Permission permission;
            String permissionGroupCd;
            List<String> permissionGroupList;
            for (final Permissions permissions : permissionsList) {
                permission = new Permission();
                permission.setPermissionCd(permissions.getPermissionCd());
                permission.setPermissionName(permissions.getPermissionName());
                permissionGroupList = new ArrayList<>();
                for (final PermissionGroups permissionGroups : permissions
                        .getPermissionGroupses()) {
                    permissionGroupCd = permissionGroups.getId()
                            .getPermissionGroupCd();
                    permissionGroupList.add(permissionGroupCd);
                    permission.setGroups(permissionGroupList);
                }
                permissionList.add(permission);
            }

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

        return permissionList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#findRole
     * (int)
     */
    @Override
    public Role findRole(int roleId) throws SharedServiceException {
        Session session = null;
        Role role = null;
        List<String> groups;
        final List<Permission> permission = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final Roles roles = (Roles) session.createCriteria(Roles.class)
                    .add(Restrictions.eq("roleId", roleId)).uniqueResult();

            if (null == roles) {
                return null;
            }

            final Set<RolePermissions> rolePermissionsSet = roles
                    .getRolePermissionses();
            Permissions permissions;
            for (final RolePermissions rolePermissions : rolePermissionsSet) {
                groups = new ArrayList<>();
                permissions = rolePermissions.getPermissions();
                if (null != permissions) {
                    permission.add(new Permission(
                            permissions.getPermissionCd(), permissions
                                    .getPermissionName(), groups));
                    for (final PermissionGroups groupsSet : permissions
                            .getPermissionGroupses()) {
                        groups.add(groupsSet.getId().getPermissionGroupCd());
                    }
                }
            }

            role = new Role(roles.getRoleId(), roles.getRoleName(), permission);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }
        return role;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#findRoleByUser
     * (int)
     */
    @Override
    public Role findRoleByUser(int userId) throws SharedServiceException {
        Session session = null;
        Role role = null;
        List<String> groups;
        final List<Permission> permission = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final UserRoles userRoles = (UserRoles) session
                    .createCriteria(UserRoles.class)
                    .add(Restrictions.eq("id.userId", userId)).uniqueResult();

            if (null == userRoles) {
                return role;
            }
            final Roles roles = userRoles.getRoles();
            final Set<RolePermissions> rolePermissionsSet = roles
                    .getRolePermissionses();

            for (final RolePermissions rolePermissions : rolePermissionsSet) {
                groups = new ArrayList<>();
                final Permissions permissions = rolePermissions
                        .getPermissions();
                for (final PermissionGroups groupsSet : permissions
                        .getPermissionGroupses()) {
                    groups.add(groupsSet.getId().getPermissionGroupCd());
                }
                permission.add(new Permission(permissions.getPermissionCd(),
                        permissions.getPermissionName(), groups));

            }

            role = new Role(roles.getRoleId(), roles.getRoleName(), permission);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }
        return role;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#findPermissions
     * (java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Permission> findPermissions(String permissionGroupCd)
            throws SharedServiceException {
        Session session = null;
        final List<Permission> list = new ArrayList<>();
        List<String> groups;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final List<PermissionGroups> permissionGroupsList = session
                    .createCriteria(PermissionGroups.class)
                    .add(Restrictions.eq("id.permissionGroupCd",
                            permissionGroupCd)).list();
            if (null == permissionGroupsList || permissionGroupsList.isEmpty()) {
                return list;
            }

            for (final PermissionGroups permissionGroups : permissionGroupsList) {

                groups = new ArrayList<String>();
                final Permissions permissions = permissionGroups
                        .getPermissions();
                for (final PermissionGroups groupsSet : permissions
                        .getPermissionGroupses()) {
                    groups.add(groupsSet.getId().getPermissionGroupCd());
                }
                list.add(new Permission(permissions.getPermissionCd(),
                        permissions.getPermissionName(), groups));

            }

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#findPermission
     * (int)
     */
    @Override
    public Permission findPermission(String permissionId)
            throws SharedServiceException {
        Permission permission = new Permission();
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final Permissions permissions = (Permissions) session
                    .createCriteria(Permissions.class)
                    .add(Restrictions.eq("permissionCd", permissionId))
                    .uniqueResult();
            if (null == permissions) {
                return permission;
            }

            final List<String> groups = new ArrayList<String>();
            for (final PermissionGroups groupsSet : permissions
                    .getPermissionGroupses()) {
                groups.add(groupsSet.getId().getPermissionGroupCd());
            }

            permission = new Permission(permissions.getPermissionCd(),
                    permissions.getPermissionName(), groups);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }
        return permission;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.wiley.gr.ace.sharedservices.service.PermissionRepository#
     * findAdditionalPermissions(int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Permission> findAdditionalPermissions(int userId, int objectId)
            throws SharedServiceException {
        Session session = null;
        List<String> groups = null;
        final List<Permission> permission = new ArrayList<Permission>();
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final List<AdditionalPermissions> additionalPermissions = session
                    .createCriteria(AdditionalPermissions.class)
                    .add(Restrictions.eq("objectId", objectId))
                    .add(Restrictions.eq("usersByUserId.userId", userId))
                    .list();
            if (null == additionalPermissions) {
                return permission;
            }

            for (final AdditionalPermissions additionalPermission : additionalPermissions) {
                groups = new ArrayList<String>();
                final Permissions permissions = additionalPermission
                        .getPermissions();
                for (final PermissionGroups groupsSet : permissions
                        .getPermissionGroupses()) {
                    groups.add(groupsSet.getId().getPermissionGroupCd());
                }
                permission.add(new Permission(permissions.getPermissionCd(),
                        permissions.getPermissionName(), groups));

            }

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }
        return permission;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#deleteRole
     * (int)
     */
    @Override
    public void deleteRole(int roleId) throws SharedServiceException {
        Session session = null;
        new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final Roles roles = (Roles) session.get(Roles.class, roleId);
            session.delete(roles);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#deletePermission
     * (int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void deletePermission(int roleId) throws SharedServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final List<RolePermissions> roles = session
                    .createCriteria(RolePermissions.class)
                    .add(Restrictions.eq("id.roleId", roleId)).list();
            for (final RolePermissions rolePermission : roles) {
                session.delete(rolePermission);
            }

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.wiley.gr.ace.sharedservices.service.PermissionRepository#
     * deletePermissionOfUser(int, int)
     */
    @Override
    public void deletePermissionOfUser(int userId, int articleId)
            throws SharedServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final AdditionalPermissions additionalPermissions = (AdditionalPermissions) session
                    .createCriteria(AdditionalPermissions.class)
                    .add(Restrictions.eq("usersByUserId.userId", userId))
                    .add(Restrictions.eq("objectId", articleId));

            session.delete(additionalPermissions);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#createNewRole
     * (java.lang.String)
     */
    @Override
    public void createNewRole(Role role) throws SharedServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();
            final Roles roles = new Roles();
            final Users users = new Users();
            users.setUserId(role.getCreatedBy());
            roles.setRoleName(role.getRoleName());
            roles.setDescription(role.getRoleDescription());
            roles.setRoleType(role.getRoleType());
            roles.setCreatedDate(role.getCreatedDate());
            roles.setUpdatedDate(role.getUpdatedDate());
            roles.setUsersByCreatedBy(users);
            roles.setUsersByUpdatedBy(users);

            session.save(roles);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.wiley.gr.ace.sharedservices.service.PermissionRepository#
     * createNewPermission (java.lang.String)
     */
    @Override
    public void createNewPermission(Permission permission)
            throws SharedServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();
            final Permissions permissions = new Permissions();
            final Users users = new Users();
            users.setUserId(permission.getCreatedBy());
            permissions.setPermissionCd(permission.getPermissionCd());
            permissions.setPermissionName(permission.getPermissionName());
            permissions.setCreatedDate(permission.getCreatedOn());
            permissions.setUpdatedDate(permission.getUpdatedOn());
            permissions.setUsersByCreatedBy(users);
            permissions.setUsersByUpdatedBy(users);

            session.save(permissions);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#updateRole
     * (int, com.wiley.gr.ace.sharedservices.service.Role)
     */
    @Override
    public void updateRole(int roleId, Role role) throws SharedServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();
            final Roles roles = new Roles();
            roles.setRoleId(roleId);
            roles.setRoleName(role.getRoleName());
            roles.setDescription(role.getRoleDescription());
            roles.setRoleType(role.getRoleType());
            roles.setCreatedDate(role.getCreatedDate());
            roles.setUpdatedDate(role.getUpdatedDate());

            session.update(roles);

            // Flush the session.
            session.flush();
            // Clear the session object.
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#updatePermission
     * (java.util.List, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updatePermission(List<Permission> permissionList, int roleId)
            throws SharedServiceException {

        // Get the session from sessionFactory pool.
        Session session = null;
        try {

            // Open the session.
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final List<RolePermissions> roles = session
                    .createCriteria(RolePermissions.class)
                    .add(Restrictions.eq("id.roleId", roleId)).list();
            for (final RolePermissions rolePermission : roles) {
                session.delete(rolePermission);
            }

            session.flush();
            session.clear();

            for (final Permission permission : permissionList) {
                final RolePermissions rolePermissions = new RolePermissions();
                final RolePermissionsId rolePermissionsId = new RolePermissionsId();
                rolePermissionsId.setPermissionCd(permission.getPermissionCd());
                rolePermissionsId.setRoleId(roleId);
                rolePermissions.setId(rolePermissionsId);
                session.save(rolePermissions);

            }

            session.flush();
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.wiley.gr.ace.sharedservices.service.PermissionRepository#
     * updateAdditionalPermissions(int, int,
     * com.wiley.gr.ace.sharedservices.service.AdditionalPermission)
     */
    @Override
    public void updateAdditionalPermissions(int userId, int objectId,
            AdditionalPermission permission) throws SharedServiceException {

        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final Users users = (Users) session.get(Users.class, userId);
            final ObjectTypes objectTypes = (ObjectTypes) session.get(
                    ObjectTypes.class, permission.getObjectTypeCd());
            final Permissions permissions = (Permissions) session.get(
                    Permissions.class, permission.getPermissionCd());

            final AdditionalPermissions additionalPermissions = new AdditionalPermissions();
            additionalPermissions.setAdditionalPermissionsId(permission
                    .getAdditionalPermissionId());
            additionalPermissions.setObjectId(objectId);
            additionalPermissions.setObjectTypes(objectTypes);
            additionalPermissions.setPermissions(permissions);
            additionalPermissions.setPermEndDt(permission
                    .getPermissionEndDate());
            additionalPermissions.setUsersByCreatedBy(users);
            additionalPermissions.setUsersByUpdatedBy(users);
            additionalPermissions.setUsersByUserId(users);
            additionalPermissions.setCreatedDate(new Date());
            additionalPermissions.setUpdatedDate(new Date());

            session.save(additionalPermissions);

            session.flush();
            session.clear();

            // Commit the transaction.
            session.getTransaction().commit();

        } catch (final Exception e) {
            // Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                // Close the session
                session.close();
            }
        }
    }
}
