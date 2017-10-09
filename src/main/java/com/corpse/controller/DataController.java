package com.corpse.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.Data;
import com.corpse.model.User;
import com.corpse.service.CollectionService;
import com.corpse.service.ContractorService;
import com.corpse.service.DealerService;
import com.corpse.service.ItemService;
import com.corpse.service.SettingService;
import com.corpse.service.StorageService;
import com.corpse.service.TradePointService;
import com.corpse.service.TradePointTypeService;
import com.corpse.service.document.ChecklistService;
import com.corpse.service.document.InventorizationService;
import com.corpse.service.document.OrderService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/alldata")
@Secured(Common.ROLE_OWNER)
public class DataController {

	@Autowired
	private TradePointService tradePointService;

	@Autowired
	private ContractorService contractorService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CollectionService collectionService;

	@Autowired
	private TradePointTypeService tradePointTypeService;
	
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private StorageService storageService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private ChecklistService checklistService;

	@Autowired
	private InventorizationService inventorizationService;

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(method = RequestMethod.GET)	
	@ResponseBody
	public Data getAll(Principal principal) {
		Map<String, String> empty = new HashMap<>();
		empty.put("all", String.valueOf(true));
		User user = Common.getUser(principal);
		Data data = new Data();
		data.setTradePoints(tradePointService.getAll(empty, user));
		data.setContractors(contractorService.getAll(user));
		data.setOrders(orderService.getFilterAll(empty, user));
		data.setInventorizations(inventorizationService.getFilterAll(empty, user));
		data.setItems(itemService.getAll());
		data.setCollections(collectionService.getAll());
		data.setTradePointTypes(tradePointTypeService.getAll());
		data.setDealers(dealerService.getAll(user));
		data.setStorage(storageService.getAll());
		data.setChecklists(checklistService.getFilterAll(empty, user));
		data.setSettings(settingService.getAll());
		return data;
	}
}
