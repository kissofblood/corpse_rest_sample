package com.corpse.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.Price;
import com.corpse.model.ResponseUDI;
import com.corpse.service.PriceService;
import com.corpse.util.Common;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api/price")
@Secured(Common.ROLE_OWNER)
public class PriceController {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private PriceService priceService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Price> getAll() {
		return priceService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Price> getByKey(@PathVariable("id") long id) {
		return priceService.getByKey(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(Principal principal,
			@RequestParam(value = "dealer", required = false) Long dealer,
			@RequestParam("price") String price) throws JsonParseException, JsonMappingException, IOException {
		List<Map<String, Object>> list = mapper.readValue(
			price,
			new TypeReference<List<Map<String, Object>>>(){}
		);
		Price p = new Price();
		p.setDealer(dealer);
		p.setPrice(list);
		return priceService.updateByKey(p, Common.getUser(principal));
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(Principal principal,
			@RequestParam(value = "dealer", required = false) Long dealer,
			@RequestParam("price") String price) throws JsonParseException, JsonMappingException, IOException {
		List<Map<String, Object>> list = mapper.readValue(
			price,
			new TypeReference<List<Map<String, Object>>>(){}
		);
		Price p = new Price();
		p.setDealer(dealer);
		p.setPrice(list);
		return priceService.insert(p, Common.getUser(principal));
	}

	@RequestMapping(value = "/{dealer}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(Principal principal,
			@PathVariable(value = "dealer", required = false) Long dealer) {
		return priceService.deleteByKey(dealer, Common.getUser(principal));
	}
}
