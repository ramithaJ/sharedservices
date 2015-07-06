package com.wiley.gr.ace.authorservices.persistence.context;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;
import com.wiley.gr.ace.authorservices.persistence.services.impl.CrossRefDAOImpl;


// TODO: Auto-generated Javadoc
/**
 * The Class CrossRefPersistenceBeanConfig.
 */
@Configuration
public class CrossRefPersistenceBeanConfig {


	/**
	 * Cross ref hibernate connection.
	 *
	 * @return the cross ref hibernate connection
	 */
	@Bean(name = "CrossRefHibernateConnection")
	public CrossRefHibernateConnection crossRefHibernateConnection() {
		return new CrossRefHibernateConnection();
	}

	
	/**
	 * Cross ref dao.
	 *
	 * @return the cross ref dao
	 */
	@Bean(name = "CrossRefDAO")
	public CrossRefDAO crossRefDAO(){
		return new CrossRefDAOImpl();
	}
	

}
