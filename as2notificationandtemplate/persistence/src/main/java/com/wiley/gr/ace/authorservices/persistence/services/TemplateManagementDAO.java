package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;

public interface TemplateManagementDAO {
	List<Template> getTemplateTags(String applicationId);

	Template getTemplate(String templateId, String applicationId);

	boolean saveOrUpdateTemplate(Template template);

	// boolean updateTemplate(String templateId,String
	// applicationId,Map<String,Object> templateMap);

	boolean deleteTemplate(String applicationId, String templateId);

	Template searchTemplate(String applicationId, String tagL1, String tagL2);
}
