package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.Tags;
import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateObj;

public interface TemplateManagementService {

	TemplateObj getTemplate(String templateId, String applicationId)
			throws Exception;

	boolean insertTemplate(TemplateObj template) throws Exception;

	boolean updateTemplate(String templateId, String applicationId,
			TemplateObj templateObj) throws Exception;

	boolean deleteTemplate(String applicationId, String templateId);

	Tags getTemplateTags(String applicationId) throws Exception;

	TemplateObj searchTemplate(String applicationId, String tagL1, String tagL2)
			throws Exception;

	TemplateObj renderTemplate(String applicationId, String templateId,
			TemplateDetails templateDetails) throws Exception;
}
