package com.corpse.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.StorageBalance;
import com.corpse.model.StorageHistory;
import com.corpse.service.BalanceService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/balance")
@Service(Common.ROLE_OWNER)
public class BalanceController {

	@Autowired
	private BalanceService balanceService;

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	@ResponseBody
	public List<StorageHistory> getHistory(@RequestParam Map<String, String> params) {
		return balanceService.getAllHistory(params);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<StorageBalance> getBalance(@RequestParam Map<String, String> params) {
		return balanceService.getAllBalance(params);
	}
}
