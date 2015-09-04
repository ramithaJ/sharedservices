/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.discount.service.impl;

import com.wiley.gr.ace.discount.common.CommonConstants;
import com.wiley.gr.ace.discount.common.CommonUtil;
import com.wiley.gr.ace.discount.exception.SharedServiceException;
import com.wiley.gr.ace.discount.model.*;
import com.wiley.gr.ace.discount.service.DiscountService;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    //Logger Instance
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountServiceImpl.class);

    /**
     * This field is use to hold the value of jdbcTemplate.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Method to get DB Connection.
     *
     * @return Database Connection
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return jdbcTemplate.getDataSource().getConnection();
    }

    /**
     * Method to close connection and callablestatement.
     *
     * @param con Connection
     * @param cs  CallableStatement
     * @throws SharedServiceException
     */
    private void closeConnection(Connection con, CallableStatement cs) throws SharedServiceException {
        if (null != con) {
            LOGGER.info(CommonConstants.CLOSE_CONNECTION);
            //Close the session
            try {
                con.close();
                cs.close();
            } catch (SQLException sqlError) {
                LOGGER.error(CommonConstants.ERROR_CLOSE_CONNECTION, sqlError);
                CommonUtil.throwError(sqlError);
            }
        }
    }


    /**
     * This method will call GET_MAX_DISCOUNT stored procedure to get max getMaxDiscountRequest based on the input parameters.
     *
     * @param getMaxDiscountRequest GetMaxDiscountRequest Payload
     * @return Service Object
     * @throws SharedServiceException
     */
    @Override
    public Service getDiscount(GetMaxDiscountRequest getMaxDiscountRequest) throws SharedServiceException {
        Service service = new Service();
        Connection con = null;
        CallableStatement cs = null;
        try {
            LOGGER.info("getGetMaxDiscountRequest ...");
            LOGGER.debug("getGetMaxDiscountRequest Request:" + getMaxDiscountRequest.toString());

            //Get the Database Connection
            con = getConnection();
            //Invoke GET_MAX_DISCOUNT Proc
            cs = con.prepareCall("{call GET_MAX_DISCOUNT(?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, getMaxDiscountRequest.getSocietyPromoCode());
            cs.setString(2, getMaxDiscountRequest.getSocietyCode());
            cs.setString(3, getMaxDiscountRequest.getInstitutionCode());
            cs.setString(4, getMaxDiscountRequest.getCountryCode());
            cs.setString(5, getMaxDiscountRequest.getJournalAcronym());
            cs.setFloat(6, getMaxDiscountRequest.getBasePrice());
            cs.setString(7, getMaxDiscountRequest.getOtherPromoCodes());
            //Register the output Parameters

            //This returns the actual value
            cs.registerOutParameter(8, OracleTypes.CURSOR);
            //This returns the error code
            cs.registerOutParameter(9, OracleTypes.VARCHAR);
            //This returns the error message
            cs.registerOutParameter(10, OracleTypes.VARCHAR);

            //Execute the proc
            cs.execute();

            //Check whether the procedure returns a value or not
            if (cs != null) {
                ResultSet cursor = (ResultSet) cs.getObject(8);
                if (null != cursor) {
                    while (cursor.next()) {
                        GetMaxDiscountResponse getMaxDiscountResponse = new GetMaxDiscountResponse();
                        getMaxDiscountResponse.setDiscountTypeName(cursor.getString(1));
                        getMaxDiscountResponse.setDiscountTypeCode(cursor.getString(2));
                        getMaxDiscountResponse.setDiscountPercentage(cursor.getString(3));
                        getMaxDiscountResponse.setDiscountValue(cursor.getString(4));
                        service.setPayload(getMaxDiscountResponse);
                    }
                }
                if (!StringUtils.isEmpty(cs.getString(9)) && !StringUtils.isEmpty(cs.getString(10))) {
                    service.setStatus(CommonConstants.ERROR);
                    service.setError(new ErrorResponse(cs.getString(9), cs.getString(10)));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Exception Occurred during getGetMaxDiscountRequest call...", e);
            CommonUtil.throwError(e);
        } finally {
            closeConnection(con, cs);
        }

        return service;

    }

    /**
     * This method will call GET_DISCOUNTED_SOCIETIES stored procedure to get list (or) particular societies getMaxDiscountRequest based on the input journalAcronym.
     *
     * @param journalAcronym Journal Acronym
     * @return Service Object
     * @throws SharedServiceException
     */
    @Override
    public Service getDiscountSocietiesForJournal(String journalAcronym) throws SharedServiceException {
        Service service = new Service();
        Connection con = null;
        CallableStatement cs = null;
        try {
            LOGGER.info("getDiscountSocietiesForJournal ...");
            LOGGER.debug("getDiscountSocietiesForJournal Request:" + journalAcronym);

            //Get the Database Connection
            con = getConnection();
            //Invoke GET_DISCOUNTED_SOCIETIES Proc
            cs = con.prepareCall("{call GET_DISCOUNTED_SOCIETIES(?,?)}");
            cs.setString(2, journalAcronym);
            //Register the output Parameters
            //This returns the actual value
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            //Execute the proc
            cs.execute();


            //Check whether the procedure returns a value or not
            if (cs != null) {
                ResultSet cursor = (ResultSet) cs.getObject(1);
                Discount discount = null;
                while (cursor.next()) {
                    GetSocietyResponse getSocietyResponse = new GetSocietyResponse();
                    List<Discount> discountList = new LinkedList<>();
                    getSocietyResponse.setSocietyCode(cursor.getString(1));
                    getSocietyResponse.setSocietyName(cursor.getString(2));
                    discount = CommonUtil.setDiscount(null, null, null, cursor.getString(3), cursor.getString(4));
                    discountList.add(discount);
                    getSocietyResponse.setDiscount(discountList);
                    service.setPayload(getSocietyResponse);
                }

            }

        } catch (Exception e) {
            LOGGER.error("Exception Occurred during getDiscountSocietiesForJournal call...", e);
            CommonUtil.throwError(e);
        } finally {
            closeConnection(con, cs);
        }

        return service;

    }

    /**
     * This method will call GET_INSTITUTION_DISCOUNTS stored procedure to get list (or) particular institution getMaxDiscountRequest based on the institution code.
     *
     * @param institutionCode GetInstitutionResponse Code
     * @return Service Object
     * @throws SharedServiceException
     */
    @Override
    public Service getInstitutionsDiscount(String institutionCode) throws SharedServiceException {
        Service service = new Service();
        Connection con = null;
        CallableStatement cs = null;
        try {
            LOGGER.info("getInstitutionsDiscount ...");
            LOGGER.debug("getInstitutionsDiscount Request:" + institutionCode);

            //Get the Database Connection
            con = getConnection();
            //Invoke GET_INSTITUTION_DISCOUNTS Proc
            cs = con.prepareCall("{call GET_INSTITUTION_DISCOUNTS(?,?)}");
            cs.setString(2, institutionCode);
            //Register the output Parameters
            //This returns the actual value
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            //Execute the proc
            cs.execute();


            //Check whether the procedure returns a value or not
            if (cs != null) {
                ResultSet cursor = (ResultSet) cs.getObject(1);
                GetInstitutionsResponse getInstitutionsResponse = new GetInstitutionsResponse();
                List<GetInstitutionResponse> getInstitutionResponseList = new LinkedList<>();
                GetInstitutionResponse getInstitutionResponse = null;
                List<Discount> discountList = null;
                Discount discount = null;
                while (cursor.next()) {
                    getInstitutionResponse = new GetInstitutionResponse();
                    discountList = new LinkedList<>();
                    getInstitutionResponse.setInstituteCode(cursor.getString(1));
                    getInstitutionResponse.setInstituteName(cursor.getString(2));
                    discount = CommonUtil.setDiscount(cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                    discountList.add(discount);
                    getInstitutionResponse.setDiscount(discountList);
                    getInstitutionResponseList.add(getInstitutionResponse);
                    getInstitutionsResponse.setInstitutions(getInstitutionResponseList);
                }
                service.setPayload(getInstitutionsResponse);
            }

        } catch (Exception e) {
            LOGGER.error("Exception Occurred during getInstitutionsDiscount call...", e);
            CommonUtil.throwError(e);
        } finally {
            closeConnection(con, cs);
        }

        return service;

    }


}
