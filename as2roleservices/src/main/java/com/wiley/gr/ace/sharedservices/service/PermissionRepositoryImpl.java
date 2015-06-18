package com.wiley.gr.ace.sharedservices.service;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.persistence.entity.Permissions;
import com.wiley.gr.ace.sharedservices.persistence.entity.Roles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Role> findRoles() throws SharedServiceException {
        Session session = null;
        List<Role> roles = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            List<Roles> rolesDB = session.createCriteria(Roles.class).list();
            if (null == rolesDB || rolesDB.isEmpty())
                return roles;

            for (Roles role : rolesDB) {
                roles.add(new Role(role.getRoleId(), role.getRoleName()));
            }

            //Flush the session.
            session.flush();
            //Clear the session object.
            session.clear();
            //Commit the transaction.
            session.getTransaction().commit();

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

        return roles;
    }

    @Override
    public Role findRole(int roleId) throws SharedServiceException {
        Session session = null;
        Role roles = null;
        try {
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            Roles roleDB = (Roles) session.createCriteria(Roles.class).add(Restrictions.eq("roleId", roleId)).uniqueResult();
            if (null == roleDB)
                return null;

            roles = new Role(roleDB.getRoleId(), roleDB.getRoleName());

            //Flush the session.
            session.flush();
            //Clear the session object.
            session.clear();
            //Commit the transaction.
            session.getTransaction().commit();

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }
        return roles;
    }

    @Override
    public List<Permission> findPermissions() throws SharedServiceException {
        Session session = null;
        List<Permission> list = new ArrayList<>();
        try {
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            List<Permissions> permissionsList = session.createCriteria(Permissions.class).list();
            if (null == permissionsList || permissionsList.isEmpty())
                return list;

            for (Permissions role : permissionsList) {
                list.add(new Permission(null, role.getPermissionCd(), role.getPermissionName(),
                        role.getPermissionGroup(), new Date()));
            }

            //Flush the session.
            session.flush();
            //Clear the session object.
            session.clear();
            //Commit the transaction.
            session.getTransaction().commit();

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

        return list;
    }

    @Override
    public Permission findPermission(String code) throws SharedServiceException {
        Session session = null;
        Permission roles = null;
        try {
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            Permissions role = (Permissions) session.createCriteria(Permissions.class)
                    .add(Restrictions.eq("permissionCd", code)).uniqueResult();
            if (null == role)
                return null;

            roles = new Permission(null, role.getPermissionCd(), role.getPermissionName(),
                    role.getPermissionGroup(), new Date());

            //Flush the session.
            session.flush();
            //Clear the session object.
            session.clear();
            //Commit the transaction.
            session.getTransaction().commit();

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }
        return roles;
    }

    @Override
    public Role createNewRole(String name) {
        return null;
    }

}
