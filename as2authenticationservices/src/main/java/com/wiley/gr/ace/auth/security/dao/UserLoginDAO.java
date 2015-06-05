package com.wiley.gr.ace.auth.security.dao;

import org.springframework.stereotype.Service;

@Service(value = "userLoginDao")
public interface UserLoginDAO {

	LockedAccountDetails userAccountDetails(String userId);
	
	boolean insertUser(String userId);
	
	boolean removeUser(String userId);
	
	boolean updateUser(String userId);
}
