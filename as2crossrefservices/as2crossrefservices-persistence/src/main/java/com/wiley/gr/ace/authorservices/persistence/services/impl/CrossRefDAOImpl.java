package com.wiley.gr.ace.authorservices.persistence.services.impl;

import static com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection.getSessionFactory;

import org.hibernate.Session;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;

public class CrossRefDAOImpl implements CrossRefDAO {

@Override
	public final ProductPersonRelations getProductPersonRelations(final Integer dhId)
			throws Exception {
		Session session = null;
		ProductPersonRelations productPersonRelations = null;
		session = getSessionFactory().openSession();
		String hql = "from ProductPersonRelations ppr where ppr.products.dhId=:dhId";
		productPersonRelations = (ProductPersonRelations) session
				.createQuery(hql).setInteger("dhId", dhId).uniqueResult();
		return productPersonRelations;
	}
	

}
