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

import com.corpse.model.Collection;
import com.corpse.model.ResponseUDI;
import com.corpse.service.CollectionService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/collection")
@Secured(Common.ROLE_OWNER)
public class CollectionController {

	@Autowired
	private CollectionService collectionService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Collection> getAll() {
		return collectionService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Collection> getByKey(@PathVariable("id") long id) {
		return collectionService.getByKey(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(@RequestParam("id") long id,
			@RequestParam("name") String name) {
		Collection collection = new Collection();
		collection.setId(id);
		collection.setName(name);
		return collectionService.updateByKey(collection);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return collectionService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(@RequestParam("name") String name) {
		Collection collection = new Collection();
		collection.setName(name);
		return collectionService.insert(collection);
	}
}
