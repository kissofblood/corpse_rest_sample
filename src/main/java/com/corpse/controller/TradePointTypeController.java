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
import com.corpse.model.TradePointType;
import com.corpse.service.TradePointTypeService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/tradepointtype")
@Secured(Common.ROLE_OWNER)
public class TradePointTypeController {

	@Autowired
	private TradePointTypeService tradePointTypeService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TradePointType> getAll() {
		return tradePointTypeService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<TradePointType> getByKey(@PathVariable("id") long id) {
		return tradePointTypeService.getByKey(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(@RequestParam("id") long id,
			@RequestParam("name") String name) {
		TradePointType tradePointType = new TradePointType();
		tradePointType.setId(id);
		tradePointType.setName(name);
		return tradePointTypeService.updateByKey(tradePointType);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return tradePointTypeService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(@RequestParam("name") String name) {
		TradePointType tradePointType = new TradePointType();
		tradePointType.setName(name);
		return tradePointTypeService.insert(tradePointType);
	}
}
