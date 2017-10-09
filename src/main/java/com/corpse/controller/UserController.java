package com.corpse.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.ResponseUDI;
import com.corpse.model.User;
import com.corpse.service.UserService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/user")
@Secured(Common.ROLE_OWNER)
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<User> getAll(Principal principal, @RequestParam Map<String, String> params) {
		return userService.getAll(params, Common.getUser(principal));
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getByKey(Principal principal, @PathVariable("username") String username) {
		return userService.getByKey(username, Common.getUser(principal));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<String>> updateByKey(@RequestBody List<User> users) {
		return userService.updateByKey(users);
	}

	@RequestMapping(value = "/update/password", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<String> updatePasswordByKey(
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		return userService.updatePasswordByKey(username, password);
	}

	@RequestMapping(value = "/update/isadmin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<String> updateIsAdminByKey(
		@RequestParam("username") String username,
		@RequestParam("isadmin") boolean isAdmin) {
		return userService.updateIsAdminByKey(username, isAdmin);
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<String> deleteByKey(@PathVariable("username") String username) {
		return userService.deleteByKey(username);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<String>> insert(@RequestBody List<User> users) {
		for(User user : users) {
			if(user.getPassword() == null || user.getPassword().isEmpty()) {
				throw new RuntimeException(user.getUsername() + " has empty passsord");
			}
		}
		return userService.insert(users);
	}
}
