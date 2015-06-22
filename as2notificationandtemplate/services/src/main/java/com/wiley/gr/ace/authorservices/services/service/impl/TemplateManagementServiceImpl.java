package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.model.Tags;
import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateVO;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.TemplateManagementDAO;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;

public class TemplateManagementServiceImpl implements TemplateManagementService {

	@Autowired(required = true)
	private TemplateManagementDAO templateManagementDAO;

	@Override
	public Tags getTemplateTags(String applicationId) throws Exception {

		List<String> tag1 = new ArrayList<String>();
		List<String> tag2 = new ArrayList<String>();
		List<Template> templateEntityList = null;
		Tags tags = null;
		List<TemplateVO> templateList = new ArrayList<TemplateVO>();
		if (!StringUtils.isEmpty(applicationId)) {
			templateEntityList = templateManagementDAO
					.getTemplateTags(applicationId);
			if (!StringUtils.isEmpty(templateEntityList)) {
				for (Template te : templateEntityList) {
					templateList.add(getTemplateVO(te));
				}
				for (TemplateVO t : templateList) {
					tag1.add(t.getTagl1());
					tag2.add(t.getTagl2());
				}

				tags = new Tags();
				tags.setTag1List(tag1);
				tags.setTag2List(tag2);
			}

		}
		return tags;

	}

	@Override
	public TemplateVO getTemplate(String templateId, String applicationId)
			throws IOException, SQLException {
		if (!StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(applicationId)) {

			return getTemplateVO(templateManagementDAO.getTemplate(templateId,
					applicationId));
		} else
			return null;

	}

	@Override
	public boolean insertTemplate(TemplateVO template) throws Exception {
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
			return templateManagementDAO.saveOrUpdateTemplate(templateEntity);
		} else
			return false;
	}

	@Override
	public boolean updateTemplate(String templateId, String applicationId,
			TemplateVO templateObj) throws Exception {

		boolean updateStatus = false;
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

		updateStatus = templateManagementDAO
				.saveOrUpdateTemplate(templateEntity);
		return updateStatus;
	}

	@Override
	public boolean deleteTemplate(String applicationId, String templateId) {
		return templateManagementDAO.deleteTemplate(applicationId, templateId);
	}

	@Override
	public TemplateVO searchTemplate(String applicationId, String tagL1,
			String tagL2) throws IOException, SQLException {

		return getTemplateVO(templateManagementDAO.searchTemplate(
				applicationId, tagL1, tagL2));

	}

	private TemplateVO getTemplateVO(Template templateEntity)
			throws IOException, SQLException {
		TemplateVO template = new TemplateVO();
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

	@Override
	public TemplateVO renderTemplate(String applicationId, String templateId,
			TemplateDetails templateDetails) throws Exception {
		if (!StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(applicationId)) {
			Template templateEntity = null;
			templateEntity = templateManagementDAO.getTemplate(templateId,
					applicationId);
			TemplateVO template = getTemplateVO(templateManagementDAO
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

	private static String clobStringConversion(Clob clb) throws IOException,
			SQLException {
		if (clb == null)
			return "";

		StringBuffer str = new StringBuffer();
		String strng;

		BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

		while ((strng = bufferRead.readLine()) != null)
			str.append(strng);

		return str.toString();
	}

}
