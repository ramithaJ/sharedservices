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
package com.wiley.gr.ace.profile.common;

import java.util.ArrayList;
import java.util.List;

import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.ErrorResponse;
import com.wiley.gr.ace.profile.model.Service;
import com.wiley.gr.ace.profile.model.updateProfileRequest;

public class CommonUtil {

	public static List<String> getEntityTypeList() {

		final List<String> entityTypeList = new ArrayList<String>();

		if (entityTypeList == null || entityTypeList.isEmpty()) {

			entityTypeList.add(CommonConstants.ENTITY_TYPE_PROFILE);
			entityTypeList.add(CommonConstants.ENTITY_TYPE_ADDRESS);
			entityTypeList.add(CommonConstants.ENTITY_TYPE_EMAIL);
			entityTypeList.add(CommonConstants.ENTITY_TYPE_FAVJOURNAL);
			entityTypeList.add(CommonConstants.ENTITY_TYPE_INTEREST);
			entityTypeList.add(CommonConstants.ENTITY_TYPE_ALERT);

		}
		return entityTypeList;
	}

	private CommonUtil() {

	}

	/**
	 * Method to set code, message & status to Service.
	 *
	 * @param code
	 *            Code
	 * @param message
	 *            Message
	 * @param status
	 *            Status
	 * @return
	 */
	public static Service setServiceMessage(String code, String message,
			String status) {
		Service service = new Service();
		ErrorResponse error = new ErrorResponse();
		if(code!=null){
		error.setErrorCode(code);
		}
		error.setErrorMessage(message);
		service.setStatus(status);
		service.setError(error);
		return service;
	}

	
	public static void validateEntity(updateProfileRequest profile)
			throws SharedServiceException {

		if (profile.getEntityId() != null || !profile.getEntityId().isEmpty()) {

			if (profile.getEntityType() != null
					|| !profile.getEntityType().isEmpty()) {

				if (!getEntityTypeList().contains(profile.getEntityType())) {

					throw new SharedServiceException(
							CommonConstants.ERROR_CODE_400,
							CommonConstants.ERROR_NOT_SUPPORT_ENTITY);
				} else if (profile.getEntityValue() == null) {

					throw new SharedServiceException(
							CommonConstants.ERROR_CODE_400,
							CommonConstants.ERROR_ENTITY_VALUE_REQUIRED);

				}
			} else {
				throw new SharedServiceException(
						CommonConstants.ERROR_CODE_400,
						CommonConstants.ERROR_ENTITY_TYPE_REQUIRED);
			}

		} else {

			throw new SharedServiceException(CommonConstants.ERROR_CODE_400,
					CommonConstants.ERROR_ENTITY_ID_REQUIRED);

		}

	}

	
}