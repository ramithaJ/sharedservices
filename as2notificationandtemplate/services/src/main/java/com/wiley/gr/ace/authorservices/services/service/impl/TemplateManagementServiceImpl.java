/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialClob;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.node.ASTReference;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.visitor.BaseVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 
 * @author virtusa version 1.0
 */
public class TemplateManagementServiceImpl implements TemplateManagementService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TemplateManagementServiceImpl.class);

    /** The template management dao. */
    @Autowired(required = true)
    private TemplateManagementDAO templateManagementDAO;

    /**
     * Gets the template tags.
     *
     * @param applicationId
     *            the application id
     * @return the template tags
     * @throws Exception
     *             the exception
     */
    @Override
    public final Tags getTemplateTags(final String applicationId)
            throws Exception {
        LOGGER.info("inside getTemplateTags Method of TemplateManagementServiceImpl");
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
     * @param templateId
     *            the template id
     * @param applicationId
     *            the application id
     * @return the template
     * @throws Exception
     *             the exception
     */
    @Override
    public final TemplateObj getTemplate(final String templateId,
            final String applicationId) throws Exception {
        LOGGER.info("inside getTemplate Method of TemplateManagementServiceImpl");
        TemplateObj template = null;
        if (!StringUtils.isEmpty(templateId)
                && !StringUtils.isEmpty(applicationId)) {

            template = getTemplateVO(templateManagementDAO.getTemplate(
                    templateId, applicationId));
        }
        return template;

    }

    /**
     * Insert template.
     *
     * @param template
     *            the template
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    @Override
    public final boolean insertTemplate(final TemplateObj template)
            throws Exception {
        LOGGER.info("inside insertTemplate Method of TemplateManagementServiceImpl");
        boolean isInserted = false;
        if (!StringUtils.isEmpty(template)) {
            Template templateEntity = new Template();
            templateEntity.setAppId(template.getAppId());
            templateEntity.setBody(new SerialClob(template.getBody()
                    .toCharArray()));
            templateEntity.setCreatedBy(template.getCreatedBy());
            templateEntity.setCreatedOn(getDate(template.getCreatedOn()));
            templateEntity.setDescription(template.getDescription());
            templateEntity.setId(template.getId());
            templateEntity.setLastModifiedOn(getDate(template
                    .getLastModifiedOn()));
            templateEntity.setModifiedBy(template.getModifiedBy());
            templateEntity.setTagl1(template.getTagl1());
            templateEntity.setTagl2(template.getTagl2());
            isInserted = templateManagementDAO
                    .saveOrUpdateTemplate(templateEntity);
        }
        return isInserted;
    }

    /**
     * Update template.
     *
     * @param templateId
     *            the template id
     * @param applicationId
     *            the application id
     * @param templateObj
     *            the template obj
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    @Override
    public final boolean updateTemplate(final String templateId,
            final String applicationId, final TemplateObj templateObj)
            throws Exception {
        LOGGER.info("inside updateTemplate Method of TemplateManagementServiceImpl");
        Template templateEntity = null;
        templateEntity = templateManagementDAO.getTemplate(templateId,
                applicationId);

        if (!StringUtils.isEmpty(templateObj)) {
            String appId = templateObj.getAppId();
            if (!StringUtils.isEmpty(appId)) {
                templateEntity.setAppId(appId);
            }
            String body = templateObj.getBody();
            if (!StringUtils.isEmpty(body)) {
                templateEntity.setBody(new SerialClob(body.toCharArray()));
            }
            String description = templateObj.getDescription();
            if (!StringUtils.isEmpty(description)) {
                templateEntity.setDescription(description);
            }
            String tagL1 = templateObj.getTagl1();
            if (!StringUtils.isEmpty(tagL1)) {
                templateEntity.setTagl1(tagL1);
            }
            String tagL2 = templateObj.getTagl2();
            if (!StringUtils.isEmpty(tagL2)) {
                templateEntity.setTagl2(tagL2);
            }
        }

        return templateManagementDAO.saveOrUpdateTemplate(templateEntity);
    }

    /**
     * Delete template.
     *
     * @param applicationId
     *            the application id
     * @param templateId
     *            the template id
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    @Override
    public final boolean deleteTemplate(final String applicationId,
            final String templateId) throws Exception {
        LOGGER.info("inside deleteTemplate Method of TemplateManagementServiceImpl");
        return templateManagementDAO.deleteTemplate(applicationId, templateId);
    }

    /**
     * Search template.
     *
     * @param applicationId
     *            the application id
     * @param tagL1
     *            the tag l1
     * @param tagL2
     *            the tag l2
     * @return the template obj
     * @throws Exception
     *             the exception
     */
    @Override
    public final TemplateObj searchTemplate(final String applicationId,
            final String tagL1, final String tagL2) throws Exception {
        LOGGER.info("inside searchTemplate Method of TemplateManagementServiceImpl");
        TemplateObj template = null;
        if (!StringUtils.isEmpty(applicationId) && !StringUtils.isEmpty(tagL1)
                && !StringUtils.isEmpty(tagL2)) {
            Template templateEntity = templateManagementDAO.searchTemplate(
                    applicationId, tagL1, tagL2);
            template = getTemplateVO(templateEntity);
        }
        return template;

    }

    /**
     * Gets the template vo.
     *
     * @param templateEntity
     *            the template entity
     * @return the template vo
     * @throws Exception
     *             the exception
     */
    private TemplateObj getTemplateVO(final Template templateEntity)
            throws Exception {
        LOGGER.info("inside getTemplateVO Method of TemplateManagementServiceImpl");
        TemplateObj template = new TemplateObj();
        String appiD = templateEntity.getAppId();
        if (!StringUtils.isEmpty(appiD)) {
            template.setAppId(appiD);
        }
        Clob body = templateEntity.getBody();
        if (!StringUtils.isEmpty(body)) {
            template.setBody(clobStringConversion(body));
        }
        String createdBY = templateEntity.getCreatedBy();
        if (!StringUtils.isEmpty(createdBY)) {
            template.setCreatedBy(createdBY);
        }
        Date createdOn = templateEntity.getCreatedOn();
        if (!StringUtils.isEmpty(createdOn)) {
            template.setCreatedOn(createdOn.toString());
        }
        String description = templateEntity.getDescription();
        if (!StringUtils.isEmpty(description)) {
            template.setDescription(description);
        }
        String id = templateEntity.getId();
        if (!StringUtils.isEmpty(id)) {
            template.setId(id);
        }
        Date lastModifiedOn = templateEntity.getLastModifiedOn();
        if (!StringUtils.isEmpty(lastModifiedOn)) {
            template.setLastModifiedOn(lastModifiedOn.toString());
        }
        String modifiedBy = templateEntity.getModifiedBy();
        if (!StringUtils.isEmpty(modifiedBy)) {
            template.setModifiedBy(modifiedBy);
        }
        String tagL1 = templateEntity.getTagl1();
        if (!StringUtils.isEmpty(tagL1)) {
            template.setTagl1(tagL1);
        }
        String tagL2 = templateEntity.getTagl2();
        if (!StringUtils.isEmpty(tagL2)) {
            template.setTagl2(tagL2);
        }
        return template;
    }

    /**
     * Render template.
     *
     * @param applicationId
     *            the application id
     * @param templateId
     *            the template id
     * @param templateDetails
     *            the template details
     * @return the template obj
     * @throws Exception
     *             the exception
     */
    @Override
    public final TemplateObj renderTemplate(final String applicationId,
            final String templateId, final TemplateDetails templateDetails)
            throws Exception {
        LOGGER.info("inside renderTemplate Method of TemplateManagementServiceImpl");
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
        } else {
            return null;
        }
    }

    /**
     * Clob string conversion.
     *
     * @param clb
     *            the clb
     * @return the string
     * @throws Exception
     *             the exception
     */
    private String clobStringConversion(final Clob clb) throws Exception {
        LOGGER.info("inside clobStringConversion Method of TemplateManagementServiceImpl");
        if (clb == null) {
            return "";
        }
        StringBuilder str = null;
        BufferedReader bufferRead = null;
        try {
            str = new StringBuilder();
            String strng;

            bufferRead = new BufferedReader(
                    clb.getCharacterStream());

            while ((strng = bufferRead.readLine()) != null) {
                str.append(strng);
            }
        } finally {
            bufferRead.close();
        }
        return str.toString();
    }

    /**
     * Gets the date.
     *
     * @param strDate
     *            the str date
     * @return the date
     * @throws Exception
     *             the exception
     */
    private Date getDate(final String strDate) throws Exception {
        LOGGER.info("inside strDate Method of TemplateManagementServiceImpl");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.parse(strDate);
    }

    /**
     * Gets the velocity vars.
     *
     * @param applicationId
     *            the application id
     * @param templateId
     *            the template id
     * @return the velocity vars
     * @throws Exception
     *             the exception
     */
    @Override
    public final ArrayList<String> getVelocityVars(final String applicationId,
            final String templateId) throws Exception {

        ArrayList<String> keysList = new ArrayList<String>();
        if (!StringUtils.isEmpty(templateId)
                && !StringUtils.isEmpty(applicationId)) {
            Template templateEntity = null;
            templateEntity = templateManagementDAO.getTemplate(templateId,
                    applicationId);

            String templateBody = clobStringConversion(templateEntity.getBody());

            RuntimeServices rs = RuntimeSingleton.getRuntimeServices();
            StringReader reader = new StringReader(templateBody);
            SimpleNode node = rs.parse(reader, "emailtemplate");
            final ArrayList<String> keys = new ArrayList<String>();
            BaseVisitor myVisitor = new BaseVisitor() {
                @Override
                public final Object visit(final ASTReference node,
                        final Object data) {
                    String key = node.literal();
                    keys.add(key);
                    return super.visit(node, data);
                }
            };
            node.jjtAccept(myVisitor, new Object());
            for (String key : keys) {
                keysList.add(key);
            }
        }

        return keysList;
    }

}
