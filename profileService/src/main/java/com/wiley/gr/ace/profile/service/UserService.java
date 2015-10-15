package com.wiley.gr.ace.profile.service;

import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.user.UpdateUserAttributes;
import com.wiley.gr.ace.profile.model.user.UpdateUserRequest;
import com.wiley.gr.ace.profile.model.user.UpdateUserResponse;
import com.wiley.gr.ace.profile.model.user.UpdateUserSecurityAttributes;
import com.wiley.gr.ace.profile.model.user.UserSearchResponse;

public interface UserService {
	
	public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest)throws SharedServiceException;
	public UserSearchResponse searchUser(String searchParam)throws SharedServiceException;
	public void updateUserName(UpdateUserSecurityAttributes updateUserSecurityAttributes) throws SharedServiceException;

}
