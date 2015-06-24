package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.BufferedReader;
import java.io.StringWriter;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.model.Tags;
import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateObj;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.TemplateManagementDAO;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;


/**
 * The Class TemplateManagementServiceImpl.
 */
public class TemplateManagementServiceImpl implements TemplateManagementService {

	/** The template management dao. */
	@Autowired(required = true)
	private TemplateManagementDAO templateManagementDAO;

	/**
	 * Gets the template tags.
	 *
	 * @param applicationId the application id
	 * @return the template tags
	 * @throws Exception the exception
	 */
	@Override
	public Tags getTemplateTags(String applicationId) throws Exception {

		List<String> tag1 = new ArrayList<String>();
		List<String> tag2 = new ArrayList<String>();
		List<Template> templateEntityList = null;
		Tags tags = null;
		if (!StringUtils.isEmpty(applicationId)) {
			templateEntityList = templateManagementDAO
					.getTemplateTags(applicationId);
			if (!StringUtils.isEmpty(templateEntityList)) {
				for (Template te : templateEntityList) {
					tag1.add(te.getTagl1());
					tag2.add(te.getTagl2());
				}

				tags = new Tags();
				tags.setTag1List(tag1);
				tags.setTag2List(tag2);
			}

		}
		return tags;

	}

	/**
	 * Gets the template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @return the template
	 * @throws Exception the exception
	 */
	@Override
	public TemplateObj getTemplate(String templateId, String applicationId)
			throws Exception {
		TemplateObj template = null;
		if (!StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(applicationId)) {

			template = getTemplateVO(templateManagementDAO.getTemplate(templateId,
					applicationId));
		} 
			return template;

	}
	/**
	 * Insert template.
	 *
	 * @param template the template
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean insertTemplate(TemplateObj template) throws Exception {
		boolean isInserted = false;
		if (!StringUtils.isEmpty(template)) {
			Template templateEntity = new Template();
			templateEntity.setAppId(template.getAppId());
			templateEntity.setBody(new SerialClob(template.getBody()
					.toCharArray()));
			templateEntity.setCreatedBy(template.getCreatedBy());
			templateEntity.setCreatedOn(template.getCreatedOn());
			templateEntity.setDescription(template.getDescription());
			templateEntity.setId(template.getId());
			templateEntity.setLastModifiedOn(template.getLastModifiedOn());
			templateEntity.setModifiedBy(template.getModifiedBy());
			templateEntity.setTagl1(template.getTagl1());
			templateEntity.setTagl2(template.getTagl2());
			isInserted = templateManagementDAO.saveOrUpdateTemplate(templateEntity);
		} 
			return isInserted;
	}

	/**
	 * Update template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @param templateObj the template obj
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean updateTemplate(String templateId, String applicationId,
			TemplateObj templateObj) throws Exception {

		Template templateEntity = null;
		templateEntity = templateManagementDAO.getTemplate(templateId,
				applicationId);

		if (!StringUtils.isEmpty(templateObj)) {
			if (!StringUtils.isEmpty(templateObj.getAppId()))
				templateEntity.setAppId(templateObj.getAppId());
			if (!StringUtils.isEmpty(templateObj.getBody()))
				templateEntity.setBody(new SerialClob(templateObj.getBody()
						.toCharArray()));
			if (!StringUtils.isEmpty(templateObj.getDescription()))
				templateEntity.setDescription(templateObj.getDescription());
			if (!StringUtils.isEmpty(templateObj.getTagl1()))
				templateEntity.setTagl1(templateObj.getTagl1());
			if (!StringUtils.isEmpty(templateObj.getTagl2()))
				templateEntity.setTagl2(templateObj.getTagl2());
		}

		return templateManagementDAO
				.saveOrUpdateTemplate(templateEntity);
	}
	/**
	 * Delete template.
	 *
	 * @param applicationId the application id
	 * @param templateId the template id
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean deleteTemplate(String applicationId, String templateId) throws Exception{
		return templateManagementDAO.deleteTemplate(applicationId, templateId);
	}

	/**
	 * Search template.
	 *
	 * @param applicationId the application id
	 * @param tagL1 the tag l1
	 * @param tagL2 the tag l2
	 * @return the template obj
	 * @throws Exception the exception
	 */
	@Override
	public TemplateObj searchTemplate(String applicationId, String tagL1,
			String tagL2) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		Template templateEntity = templateManagementDAO.searchTemplate(applicationId, tagL1, tagL2);
		return modelMapper.map(templateEntity, TemplateObj.class);


	}

	/**
	 * Gets the template vo.
	 *
	 * @param templateEntity the template entity
	 * @return the template vo
	 * @throws Exception the exception
	 */
	private TemplateObj getTemplateVO(Template templateEntity)
			throws Exception {
		TemplateObj template = new TemplateObj();
		template.setAppId(templateEntity.getAppId());
		template.setBody(clobStringConversion(templateEntity.getBody()));
		template.setCreatedBy(templateEntity.getCreatedBy());
		template.setCreatedOn(templateEntity.getCreatedOn());
		template.setDescription(templateEntity.getDescription());
		template.setId(templateEntity.getId());
		template.setLastModifiedOn(templateEntity.getLastModifiedOn());
		template.setModifiedBy(templateEntity.getModifiedBy());
		template.setTagl1(templateEntity.getTagl1());
		template.setTagl2(templateEntity.getTagl2());
		return template;
	}

	/**
	 * Render template.
	 *
	 * @param applicationId the application id
	 * @param templateId the template id
	 * @param templateDetails the template details
	 * @return the template obj
	 * @throws Exception the exception
	 */
	@Override
	public TemplateObj renderTemplate(String applicationId, String templateId,
			TemplateDetails templateDetails) throws Exception {
		if (!StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(applicationId)) {
			Template templateEntity = null;
			templateEntity = templateManagementDAO.getTemplate(templateId,
					applicationId);
			TemplateObj template = getTemplateVO(templateManagementDAO
					.getTemplate(templateId, applicationId));

			VelocityContext vCtx = new VelocityContext();

			for (int i = 0; i < templateDetails.getFieldList().size(); i++) {
				String tempFieldIdentifier = "field" + String.valueOf(i);
				vCtx.put(tempFieldIdentifier, templateDetails.getFieldList()
						.get(i));
			}

			StringWriter sw = new StringWriter();
			String templateStr = clobStringConversion(templateEntity.getBody());

			Velocity.evaluate(vCtx, sw, "template", templateStr);

			template.setBody(sw.toString());

			return template;
		} else
			return null;
	}

	/**
	 * Clob string conversion.
	 *
	 * @param clb the clb
	 * @return the string
	 * @throws Exception the exception
	 */
	private static String clobStringConversion(Clob clb) throws Exception
			 {
		if (clb == null)
			return "";

		StringBuilder str = new StringBuilder();
		String strng;

		BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

		while ((strng = bufferRead.readLine()) != null)
			str.append(strng);
		bufferRead.close();
		return str.toString();
		
	}

}
