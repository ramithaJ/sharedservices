package com.wiley.gr.ace.sharedservices.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.persistence.entity.AdditionalPermissions;
import com.wiley.gr.ace.sharedservices.persistence.entity.PermissionGroups;
import com.wiley.gr.ace.sharedservices.persistence.entity.Permissions;
import com.wiley.gr.ace.sharedservices.persistence.entity.RolePermissions;
import com.wiley.gr.ace.sharedservices.persistence.entity.RolePermissionsId;
import com.wiley.gr.ace.sharedservices.persistence.entity.Roles;
import com.wiley.gr.ace.sharedservices.persistence.entity.UserRoles;

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
        final List<Role> roles = new ArrayList<>();
        try {

            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final List<Roles> rolesList = session.createCriteria(Roles.class)
                    .list();
            if (null == rolesList || rolesList.isEmpty()) {
                return roles;
            }

            List<String> groups = null;
            Permissions permissions = null;
            List<Permission> permission = null;
            Set<RolePermissions> rolePermissionsSet = null;

            for (final Roles role : rolesList) {
                permission = new ArrayList<>();
                rolePermissionsSet = role.getRolePermissionses();
                for (final RolePermissions rolePermissions : rolePermissionsSet) {
                    groups = new ArrayList<String>();
                    for (final PermissionGroups groupsSet : rolePermissions
                            .getPermissions().getPermissionGroupses()) {
                        groups.add(groupsSet.getId().getPermissionGroupCd());
                    }
                    permissions = rolePermissions.getPermissions();
                    permission.add(new Permission(
                            permissions.getPermissionCd(), permissions
                            .getPermissionName(), groups));
                }
                roles.add(new Role(role.getRoleId(), role.getRoleName(),
                        permission));
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

        return roles;
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
                return role;
            }
            final Set<RolePermissions> rolePermissionsSet = roles
                    .getRolePermissionses();

            for (final RolePermissions rolePermissions : rolePermissionsSet) {
                groups = new ArrayList<String>();
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
     * com.wiley.gr.ace.sharedservices.service.PermissionRepository#findRoleOfUser
     * (int)
     */
    @Override
    public Role findRoleOfUser(int userId) throws SharedServiceException {
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
                groups = new ArrayList<String>();
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
    public List<Permission> findPermission(int roleId)
            throws SharedServiceException {
        Session session = null;
        final List<Permission> permission = new ArrayList<Permission>();
        List<String> groups;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final Roles roles = (Roles) session.createCriteria(Roles.class)
                    .add(Restrictions.eq("roleId", roleId)).uniqueResult();
            if (null == roles) {
                return permission;
            }
            final Set<RolePermissions> rolePermissionsSet = roles
                    .getRolePermissionses();

            for (final RolePermissions rolePermissions : rolePermissionsSet) {
                groups = new ArrayList<String>();
                final Permissions permissions = rolePermissions
                        .getPermissions();
                for (final PermissionGroups groupsSet : permissions
                        .getPermissionGroupses()) {
                    groups.add(groupsSet.getId().getPermissionGroupCd());
                }
                permission.add(new Permission(permissions.getPermissionCd(),
                        permissions.getPermissionName(), groups));

            }

            /*
             * roles = new Permission(null, role.getPermissionCd(),
             * role.getPermissionName(), role.getPermissionGroup(), new Date());
             */
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
    @Override
    public void deletePermission(int roleId) throws SharedServiceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            final RolePermissions roles = (RolePermissions) session.get(
                    RolePermissions.class, roleId);
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
    public Role createNewRole(String name) {
        return null;
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

            final Set<RolePermissions> rolePermissionses = new HashSet<RolePermissions>(
                    0);
            final RolePermissions rolePermissions = new RolePermissions();
            final RolePermissionsId rolePermissionsId = new RolePermissionsId();

            for (final Permission permission : role.getPermissions()) {
                rolePermissionsId.setPermissionCd(permission.getCode());
                rolePermissionsId.setRoleId(roleId);
                rolePermissions.setId(rolePermissionsId);
                rolePermissionses.add(rolePermissions);
            }

            roles.setRolePermissionses(rolePermissionses);

            session.saveOrUpdate(roles);

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
}
