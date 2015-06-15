package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateVO;

public interface TemplateManagementService {
	void getTags();

	TemplateVO getTemplate(String templateId, String applicationId);

	boolean insertId();

	boolean updateId();

	boolean deleteTemplate(String templateId, String applicationId);

	TemplateVO searchTemplate(String applicationId, String tagL1, String tagL2);

	TemplateVO renderTemplate(String applicationId, String templateId,
			TemplateDetails templateDetails) throws Exception;
}
