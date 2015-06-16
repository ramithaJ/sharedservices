package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;
import java.util.Map;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;

public interface TemplateManagementDAO {
	List<Template> getTemplateTags(String applicationId);

	Template getTemplate(String templateId, String applicationId);

	boolean insertTemplate(Template template);

	boolean updateTemplate(String templateId,String applicationId,Map<String,Object> templateMap);

	boolean deleteTemplate(String templateId, String applicationId);

	Template searchTemplate(String applicationId, String tagL1, String tagL2);
}
