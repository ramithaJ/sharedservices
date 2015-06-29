package com.wiley.gr.ace.authorservices.persistence.services;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;

public interface CrossRefDAO {
	ProductPersonRelations getProductPersonRelations(Integer dhId) throws Exception;
}
