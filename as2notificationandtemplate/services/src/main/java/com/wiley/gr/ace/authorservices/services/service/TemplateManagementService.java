package com.wiley.gr.ace.authorservices.services.service;

import java.util.Map;

import com.wiley.gr.ace.authorservices.model.Tags;
import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateVO;

public interface TemplateManagementService {

	TemplateVO getTemplate(String templateId, String applicationId) throws Exception;

	boolean insertTemplate(TemplateVO template) throws Exception;

	boolean updateTemplate(String templateId,String applicationId,Map<String,Object> templateMap);

	boolean deleteTemplate(String templateId, String applicationId);
	
	Tags getTemplateTags(String applicationId) throws Exception;

	TemplateVO searchTemplate(String applicationId, String tagL1, String tagL2) throws Exception;

	TemplateVO renderTemplate(String applicationId, String templateId,
			TemplateDetails templateDetails) throws Exception;
}
