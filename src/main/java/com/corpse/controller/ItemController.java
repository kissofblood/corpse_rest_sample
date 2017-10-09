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

import com.corpse.model.Item;
import com.corpse.model.ResponseUDI;
import com.corpse.service.ItemService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/item")
@Secured(Common.ROLE_OWNER)
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Item> getAll() {
		return itemService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Item> getByKey(@PathVariable("id") long id) {
		return itemService.getByKey(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("article") String article,
			@RequestParam("collection") Long collection,
			@RequestParam("image") String image,
			@RequestParam("groupId") Long groupId) {
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		item.setArticle(article);
		item.setCollection(collection);
		item.setImage(image);
		item.setGroupId(groupId);
		return itemService.updateByKey(item);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return itemService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(
			@RequestParam("name") String name,
			@RequestParam("article") String article,
			@RequestParam("collection") Long collection,
			@RequestParam("image") String image,
			@RequestParam("groupId") Long groupId) {
		Item item = new Item();
		item.setName(name);
		item.setArticle(article);
		item.setCollection(collection);
		item.setImage(image);
		item.setGroupId(groupId);
		return itemService.insert(item);
	}
}
