/**
 * 
 */
package com.wiley.gr.ace.auth.security.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author kpshiva
 *
 */
public class UserLoginDAOImpl implements UserLoginDAO {
	
	
	@Override
	public LockedAccountDetails userAccountDetails(String userId) {

		Session session = null;
		LockedAccountDetails lockedAccountDetails = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			lockedAccountDetails = (LockedAccountDetails) session.get(LockedAccountDetails.class, userId);
		}finally{
			if (session != null) {
                session.flush();
                session.close();
            }
		}
		return lockedAccountDetails;
	}	
	
	

	@Override
	public boolean insertUser(String userId) {
		
		Session session = null;
		Transaction transaction = null;
		LockedAccountDetails lockedAccountDetails = new LockedAccountDetails();
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			lockedAccountDetails.setUserId(userId);
			lockedAccountDetails.setLoginAttemptTime(new Date());
			lockedAccountDetails.setInvalidLoginCount(1);
			lockedAccountDetails.setCreatedDate(new Date());
			session.save(lockedAccountDetails);
			transaction.commit();
		}finally{
			if (session != null) {
                session.flush();
                session.close();
            }
		}
		return true;
		
	}


	@Override
	public boolean removeUser(String userId) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			LockedAccountDetails lockedAccountDetails = (LockedAccountDetails) session.get(LockedAccountDetails.class, userId);
			session.delete(lockedAccountDetails);
			transaction.commit();
		}finally{
			if (session != null) {
                session.flush();
                session.close();
            }
		}
		return true;
	}


	@Override
	public boolean updateUser(String userId) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateConnection.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			LockedAccountDetails lockedAccountDetails = (LockedAccountDetails) session.get(LockedAccountDetails.class, userId);
			int count = lockedAccountDetails.getInvalidLoginCount();
			lockedAccountDetails.setInvalidLoginCount(++count);
			lockedAccountDetails.setLoginAttemptTime(new Date());
			lockedAccountDetails.setUpdatedDate(new Date());
			session.update(lockedAccountDetails);
			transaction.commit();
		}finally{
			if (session != null) {
                session.flush();
                session.close();
            }
		}
		return true;
	}

	public static void main(String[] args) {
		
		UserLoginDAOImpl userLoginDAOImpl = new UserLoginDAOImpl();
		LockedAccountDetails lockedAccountDetails = userLoginDAOImpl.userAccountDetails("shiva@gmail.com");
		System.err.println(lockedAccountDetails.getLoginAttemptTime());
	}

}
