package com.wiley.gr.ace.authorservices.persistence.services;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;

public interface TemplateManagementDAO {
	void getTags();

	Template getTemplate(String templateId, String applicationId);

	boolean insertId(String id);

	boolean updateId(String id);

	boolean deleteTemplate(String templateId, String applicationId);

	Template searchTemplate(String applicationId, String tagL1, String tagL2);
}
