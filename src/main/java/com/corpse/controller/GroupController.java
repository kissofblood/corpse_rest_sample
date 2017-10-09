package com.corpse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.Group;
import com.corpse.model.ResponseUDI;
import com.corpse.service.GroupService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/group")
@Secured(Common.ROLE_OWNER)
public class GroupController {

	@Autowired
	private GroupService groupService;

	@RequestMapping(method = RequestMethod.GET)	
	@ResponseBody
	public List<Group> getAll() {
		return groupService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Group> getByKey(@PathVariable("id") long id) {
		return groupService.getByKey(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(@RequestParam("id") long id,
			@RequestParam("name") String name) {
		Group group = new Group();
		group.setId(id);
		group.setName(name);
		return groupService.updateByKey(group);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return groupService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(@RequestParam("name") String name) {
		Group group = new Group();
		group.setName(name);
		return groupService.insert(group);
	}
}
