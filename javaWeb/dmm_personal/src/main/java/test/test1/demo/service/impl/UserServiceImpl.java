package test.test1.demo.service.impl;

import org.springframework.stereotype.Service;

import test.test1.demo.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService{

	public String testString() {
		return "ddddd";
	}

	public String testString(String aaa) {
		return aaa;
	}
	
}
