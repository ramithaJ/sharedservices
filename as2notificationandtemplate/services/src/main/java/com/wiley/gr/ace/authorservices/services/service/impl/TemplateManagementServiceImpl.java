package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;

import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateVO;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.TemplateManagementDAO;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;

public class TemplateManagementServiceImpl implements TemplateManagementService {

	@Autowired(required = true)
	private TemplateManagementDAO templateManagementDAO;
	TemplateVO template = null;

	@Override
	public void getTags() {
		// TODO Auto-generated method stub

	}

	@Override
	public TemplateVO getTemplate(String templateId, String applicationId) {

		return getTemplateVO(templateManagementDAO.getTemplate(templateId,
				applicationId));

	}

	@Override
	public boolean insertId() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateId() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTemplate(String templateId, String applicationId) {
		return templateManagementDAO.deleteTemplate(templateId, applicationId);
	}

	@Override
	public TemplateVO searchTemplate(String applicationId, String tagL1,
			String tagL2) {

		return getTemplateVO(templateManagementDAO.searchTemplate(
				applicationId, tagL1, tagL2));

	}

	private TemplateVO getTemplateVO(Template templateEntity) {
		template = new TemplateVO();
		template.setAppId(templateEntity.getAppId());
		template.setBody(templateEntity.getBody().toString());
		template.setCreatedBy(template.getCreatedBy());
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
		Template templateEntity = null;
		templateEntity = templateManagementDAO.getTemplate(templateId,
				applicationId);
		TemplateVO template = new TemplateVO();

		VelocityContext vCtx = new VelocityContext();
		
		for(int i = 0; i< templateDetails.getFieldList().size();i++){
			String tempFieldIdentifier = "field" + String.valueOf(i);
			vCtx.put(tempFieldIdentifier, templateDetails.getFieldList().get(i));
		}
		
		StringWriter sw = new StringWriter();
		String templateStr = templateEntity.getBody().getSubString(0, (int) templateEntity.getBody().length());
		
		Velocity.evaluate(vCtx, sw, "template", templateStr);
		
		template.setBody(sw.toString());

		return template;
	}

}
