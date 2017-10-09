package com.corpse.controller.document;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.ResponseUDI;
import com.corpse.model.document.Document;
import com.corpse.model.document.ResponseUDIDocument;
import com.corpse.service.document.ServiceDocument;
import com.corpse.util.Common;

public abstract class ControllerDocument<T extends Document> {

	protected ServiceDocument<T> service; 

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<T> getAll(Principal principal, @RequestParam Map<String, String> params) {
		return service.getFilterAll(params, Common.getUser(principal));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<T> getByKey(Principal principal, @PathVariable("id") long id) {
		return service.getFilterByKey(id, Common.getUser(principal));
	}

	@RequestMapping(value = "/insert/object", method = RequestMethod.POST)	
	@ResponseBody
	public List<ResponseUDIDocument> insertObjects(@RequestBody List<T> docs) {
		return service.insertObjects(docs);
	}

	@RequestMapping(value = "/update/object", method = RequestMethod.POST)	
	@ResponseBody
	public List<ResponseUDI<Long>> updateObjects(@RequestBody List<T> docs) {
		return service.updateObjects(docs);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)	
	@ResponseBody
	public ResponseUDI<Long> deleteByKeys(@RequestBody List<Long> ids) {
		return service.deleteByKeys(ids);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return service.deleteByKey(id);
	}
}
