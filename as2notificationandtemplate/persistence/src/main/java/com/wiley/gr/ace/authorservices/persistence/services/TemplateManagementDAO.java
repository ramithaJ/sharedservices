package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;

public interface TemplateManagementDAO {
	List<Template> getTemplateTags(String applicationId) throws Exception;

	Template getTemplate(String templateId, String applicationId) throws Exception;

	boolean saveOrUpdateTemplate(Template template) throws Exception;

	boolean deleteTemplate(String applicationId, String templateId) throws Exception;

	Template searchTemplate(String applicationId, String tagL1, String tagL2) throws Exception;
}
