package com.wiley.gr.ace.authorservices.persistence.context;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;
import com.wiley.gr.ace.authorservices.persistence.services.impl.CrossRefDAOImpl;


@Configuration
public class CrossRefPersistenceBeanConfig {


	@Bean(name = "CrossRefHibernateConnection")
	public CrossRefHibernateConnection crossRefHibernateConnection() {
		return new CrossRefHibernateConnection();
	}

	
	@Bean(name = "CrossRefDAO")
	public CrossRefDAO crossRefDAO(){
		return new CrossRefDAOImpl();
	}
	

}
