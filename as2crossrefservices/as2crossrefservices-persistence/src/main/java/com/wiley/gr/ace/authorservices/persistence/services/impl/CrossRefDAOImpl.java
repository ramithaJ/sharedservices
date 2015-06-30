package com.wiley.gr.ace.authorservices.persistence.services.impl;

import static com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection.getSessionFactory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;

public class CrossRefDAOImpl implements CrossRefDAO {

	@Override
	public final List<ProductPersonRelations> getProductPersonRelations(
			final Integer dhId) throws Exception {
		List<ProductPersonRelations> productPersonRelationList = new ArrayList<ProductPersonRelations>();
		Session session = getSessionFactory().openSession();
		String hql = "from ProductPersonRelations ppr where ppr.products.dhId=:dhId";
		productPersonRelationList = session.createQuery(hql)
				.setInteger("dhId", dhId).list();
		return productPersonRelationList;
	}
}
