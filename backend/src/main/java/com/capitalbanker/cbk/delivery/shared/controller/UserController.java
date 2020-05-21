package com.capitalbanker.cbk.delivery.shared.controller;


import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;
import com.capitalbanker.cbk.delivery.shared.model.UserObject;
import com.capitalbanker.cbk.delivery.shared.repository.RoleRepository;
import com.capitalbanker.cbk.delivery.shared.repository.UserRepository;
import com.capitalbanker.cbk.delivery.shared.response.ServiceResponseBase;
import com.capitalbanker.cbk.delivery.shared.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private UserRepository userRepo;
	
@RequestMapping(value = "/allusers", method = RequestMethod.GET)
public ServiceResponseBase<List<UserObject>> getAllUsers(){
	
	List<UserObject> user=  userService.getAllUsers();
		return new ServiceResponseBase<List<UserObject>>(user,null);
		
	}

@RequestMapping(value = "/getUsersList", method = RequestMethod.GET)
public ServiceResponseBase<List<CustomIdFullName>> getUsersList(@RequestParam String fullName){
	
	return new ServiceResponseBase<List<CustomIdFullName>>( userService.getUsersList(fullName),"null");
		
	}

@RequestMapping(value = "/role-code", method = RequestMethod.POST)
public ServiceResponseBase<String> getUserRoleCode(@RequestBody String fullName) {
	String userRole =userRepo.findByUsername(fullName).getRole().getCode();

	return new ServiceResponseBase<String>(userRole,"");
		
}

@RequestMapping(value = "/role", method = RequestMethod.POST)
public ServiceResponseBase<String> getUserRole(@RequestBody String fullName) {
	String userRole =userRepo.findByUsername(fullName).getRole().getDescription();

	return new ServiceResponseBase<String>(userRole,"");
		
}

@RequestMapping(value = "/insert", method = RequestMethod.POST)
public ServiceResponseBase<String> insertUser(@RequestBody UserObject user,@RequestParam String roleId) throws Exception {
try {
this.userService.insertNewUser(user, roleId);	
	return new ServiceResponseBase<String>("Successfully ","");
}
catch(EntityExistsException e) {
	throw new EntityExistsException("Username already exists");
}
	
}

@GetMapping(value="/roles")
public ServiceResponseBase<List<CustomIdFullName>> getAllRoles(){
	
	return new ServiceResponseBase<List<CustomIdFullName>>( roleRepo.getRoles(),"null");
		
	}




}
