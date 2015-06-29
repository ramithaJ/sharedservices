package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;


/**
 * The Interface TemplateManagementDAO.
 */
public interface TemplateManagementDAO {
	
	/**
	 * Gets the template tags.
	 *
	 * @param applicationId the application id
	 * @return the template tags
	 * @throws Exception the exception
	 */
	List<Template> getTemplateTags(String applicationId) throws Exception;

	/**
	 * Gets the template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @return the template
	 * @throws Exception the exception
	 */
	Template getTemplate(String templateId, String applicationId) throws Exception;

	/**
	 * Save or update template.
	 *
	 * @param template the template
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean saveOrUpdateTemplate(Template template) throws Exception;

	/**
	 * Delete template.
	 *
	 * @param applicationId the application id
	 * @param templateId the template id
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean deleteTemplate(String applicationId, String templateId) throws Exception;

	/**
	 * Search template.
	 *
	 * @param applicationId the application id
	 * @param tagL1 the tag l1
	 * @param tagL2 the tag l2
	 * @return the template
	 * @throws Exception the exception
	 */
	Template searchTemplate(String applicationId, String tagL1, String tagL2) throws Exception;
}
