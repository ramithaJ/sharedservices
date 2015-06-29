package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.Tags;
import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateObj;


/**
 * The Interface TemplateManagementService.
 */
public interface TemplateManagementService {

	/**
	 * Gets the template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @return the template
	 * @throws Exception the exception
	 */
	TemplateObj getTemplate(String templateId, String applicationId)
			throws Exception;

	/**
	 * Insert template.
	 *
	 * @param template the template
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean insertTemplate(TemplateObj template) throws Exception;

	/**
	 * Update template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @param templateObj the template obj
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean updateTemplate(String templateId, String applicationId,
			TemplateObj templateObj) throws Exception;

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
	 * Gets the template tags.
	 *
	 * @param applicationId the application id
	 * @return the template tags
	 * @throws Exception the exception
	 */
	Tags getTemplateTags(String applicationId) throws Exception;

	/**
	 * Search template.
	 *
	 * @param applicationId the application id
	 * @param tagL1 the tag l1
	 * @param tagL2 the tag l2
	 * @return the template obj
	 * @throws Exception the exception
	 */
	TemplateObj searchTemplate(String applicationId, String tagL1, String tagL2)
			throws Exception;

	/**
	 * Render template.
	 *
	 * @param applicationId the application id
	 * @param templateId the template id
	 * @param templateDetails the template details
	 * @return the template obj
	 * @throws Exception the exception
	 */
	TemplateObj renderTemplate(String applicationId, String templateId,
			TemplateDetails templateDetails) throws Exception;
}
