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

import com.corpse.model.ResponseUDI;
import com.corpse.model.Storage;
import com.corpse.service.StorageService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/storage")
@Secured(Common.ROLE_OWNER)
public class StorageController {

	@Autowired
	private StorageService storageService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Storage> getAll() {
		return storageService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Storage> getByKey(@PathVariable("id") long id) {
		return storageService.getByKey(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(@RequestParam("id") long id,
			@RequestParam("name") String name) {
		Storage storage = new Storage();
		storage.setId(id);
		storage.setName(name);
		return storageService.updateByKey(storage);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return storageService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(@RequestParam("name") String name) {
		Storage storage = new Storage();
		storage.setName(name);
		return storageService.insert(storage);
	}
}
