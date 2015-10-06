/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.auth.security.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

/**
 * @author Virtusa
 *
 */
public class UserLoginDAOImpl implements UserLoginDAO {

    /**
     * This method inserts the user
     *
     * @param userId
     * @param appKey
     * @return boolean
     */
    @Override
    public boolean insertUser(final String userId, final String appKey) {

        Session session = null;
        Transaction transaction = null;
        final LockedAccountDetails lockedAccountDetails = new LockedAccountDetails();
        try {
            session = HibernateConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            lockedAccountDetails.setUserId(userId);
            lockedAccountDetails.setAppKey(appKey);
            lockedAccountDetails.setInvalidLoginCount(1);
            //lockedAccountDetails.setInvalidSecurityQueCount(0);
            lockedAccountDetails.setLoginAttemptTime(new Date());
            lockedAccountDetails.setCreatedDate(new Date());
            session.saveOrUpdate(lockedAccountDetails);
            transaction.commit();
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }

        return true;
    }

    /**
     * This method removes the user
     *
     * @param userId
     * @return boolean
     */
    @Override
    public boolean removeUser(final String userId) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            final LockedAccountDetails lockedAccountDetails = (LockedAccountDetails) session
                    .get(LockedAccountDetails.class, userId);
            session.delete(lockedAccountDetails);
            transaction.commit();
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }

        return true;
    }

    /**
     * This method updates the time stamp
     *
     * @param userId
     * @return boolean
     */
    @Override
    public boolean updateTimeStamp(final String userId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            final LockedAccountDetails lockedAccountDetails = (LockedAccountDetails) session
                    .get(LockedAccountDetails.class, userId);
            int count = lockedAccountDetails.getInvalidLoginCount();
            lockedAccountDetails.setInvalidLoginCount(++count);
            lockedAccountDetails.setLockedTime(new Date());
            lockedAccountDetails.setUpdatedDate(new Date());
            session.update(lockedAccountDetails);
            transaction.commit();
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }

        return true;
    }

    /**
     * This method update the user
     *
     * @param userId
     * @return boolean
     */
    @Override
    public boolean updateUser(final String userId) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            final LockedAccountDetails lockedAccountDetails = (LockedAccountDetails) session
                    .get(LockedAccountDetails.class, userId);
            int count = lockedAccountDetails.getInvalidLoginCount();
            lockedAccountDetails.setInvalidLoginCount(++count);
            lockedAccountDetails.setLoginAttemptTime(new Date());
            lockedAccountDetails.setUpdatedDate(new Date());
            session.update(lockedAccountDetails);
            transaction.commit();
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }

        return true;
    }

    /**
     * This method returns the user account details
     *
     * @param userId
     * @return LockedAccountDetails
     */
    @Override
    public LockedAccountDetails userAccountDetails(final String userId) {

        Session session = null;
        LockedAccountDetails lockedAccountDetails = null;
        try {
            session = HibernateConnection.getSessionFactory().openSession();
            lockedAccountDetails = (LockedAccountDetails) session.get(
                    LockedAccountDetails.class, userId);
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }

        return lockedAccountDetails;
    }
}