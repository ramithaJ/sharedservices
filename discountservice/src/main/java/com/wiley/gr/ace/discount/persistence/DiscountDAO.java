package com.wiley.gr.ace.discount.persistence;

import static com.wiley.gr.ace.discount.persistence.connection.HibernateConnection.getSessionFactory;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.procedure.ProcedureCall;

import com.wiely.gr.ace.discount.service.model.DiscountDetails;




public class DiscountDAO {
	
	
    public final List<String> getDiscSocietiesForJournal() {

	        Session session = null;
	        List<String> lookupList = null;
	        try {
	            session = getSessionFactory().openSession();
	            
	            System.out.println("test");
	            
	            //ProcedureCall query = session.getNamedProcedureCall("GET_DISCOUNTED_SOCIETIES");
	 
	            /*Query query = session.createSQLQuery("CALL GET_DISCOUNTED_SOCIETIES(:stockCode)")
	            		.addEntity(Stock.class)
	            		.setParameter("stockCode", "7277");
	            */	 
	            
	            		
	   /*         Criteria criteria = session.createCriteria(LookupValues.class);
	            criteria.add(Restrictions.eq("lookupKey", keyName));
	            lookupList = criteria.list();
	   */         return lookupList;
	        } finally {
	            if (session != null) {
	                session.flush();
	                session.close();
	            }
	        }
	    }



    private DiscountDetails getDiscountDetails(){
    	
    	DiscountDetails details = new DiscountDetails();
    	
    	details.setPromoCode("OSF50");
    	details.setDiscountTypeCode("SOCI");
    	details.setDiscountValueType("A");
    	details.setDiscountType("Society");
    	details.setDiscountValue("500");
    	details.setStartdate("20-MAY-15 12.00.00.000000000 AM");
    	details.setEndDate("16-JUL-15 08.40.27.000000000 AM");
    	details.setArticlePublisheddate("20-MAY-15 12.00.00.000000000 AM");
    	details.setCountryCode("USA");
    	details.setSocietyCode("Academy for Eating Disorders");
    	details.setJournalAcronym("EAT");
    	details.setJournalType("OO");
    	details.setInstitutionCode("TEST CODE1");
    	details.setInstitutionName("Test Name");
    	
    	return details;
    }
    
    
    
    
    public final void UploadDiscounts() {

    	DiscountDetails discountDetail = getDiscountDetails();
    	
  
        Transaction tx = null;
    	
        Session session = null;
        try {
            session = getSessionFactory().openSession();

             tx = session.beginTransaction();
            
            session.save(discountDetail);
            tx.commit();
          
        }catch(HibernateException e){  
        	
        	 if (tx!=null) tx.rollback();
             e.printStackTrace();
        	
            
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }



    

}
