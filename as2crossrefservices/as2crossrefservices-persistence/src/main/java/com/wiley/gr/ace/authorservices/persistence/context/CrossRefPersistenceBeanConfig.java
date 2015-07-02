package com.wiley.gr.ace.authorservices.persistence.context;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO1;
import com.wiley.gr.ace.authorservices.persistence.services.impl.CrossRefDAOImpl;
import com.wiley.gr.ace.authorservices.persistence.services.impl.CrossRefDAOImpl1;


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
	
	@Bean(name = "CrossRefDAO1")
	public CrossRefDAO1 crossRefDAO1(){
		return new CrossRefDAOImpl1();
	}


}
