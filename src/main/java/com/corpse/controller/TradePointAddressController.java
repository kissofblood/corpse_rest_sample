package com.corpse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.TradePointAddress;
import com.corpse.service.TradePointAddressService;

@Controller
@RequestMapping("api/addresses")
public class TradePointAddressController {

	@Autowired
	private TradePointAddressService tradePointAddressService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TradePointAddress> getAll() {
		return tradePointAddressService.getAll();
	}
}
