package com.wiley.gr.ace.profile.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.wiley.gr.ace.profile.common.CommonConstants;
import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.user.UpdateUserAttributes;
import com.wiley.gr.ace.profile.model.user.UpdateUserRequest;
import com.wiley.gr.ace.profile.model.user.UpdateUserResponse;
import com.wiley.gr.ace.profile.model.user.UpdateUserSecurityAttributes;
import com.wiley.gr.ace.profile.model.user.UserSearchResponse;
import com.wiley.gr.ace.profile.service.UserService;

public class UserServiceImpl implements UserService {

	  /** the industrieskey. */
    @Value("${userDomain.value}")
    private String userDomain;

    @Value("${userSearch.value}")
    private String userSearch;
	
    @Value("${updateUserId.valu}")
    private String updateUserName;
	
    
	@Override
	public UpdateUserResponse updateUser(UpdateUserRequest updateUseRequest) throws SharedServiceException {
		
		String url = userDomain;
		UpdateUserResponse response = (UpdateUserResponse) ServiceUtil.postData(url, updateUseRequest, UpdateUserResponse.class);
		return response;
	}

	@Override
	public UserSearchResponse searchUser(String searchParam) throws SharedServiceException {
		
		String searchQuary="?Email=".concat(searchParam);
		String url = userSearch.concat(searchQuary);
		UserSearchResponse user = (UserSearchResponse)ServiceUtil.getData(url, UserSearchResponse.class);
		return user; 
	}

	@Override
	public void updateUserName(UpdateUserSecurityAttributes securityAttributes) throws SharedServiceException {
		
		String url = userDomain;
		ServiceUtil.postData(url, securityAttributes, UpdateUserSecurityAttributes.class);		
		
	}
	

}
