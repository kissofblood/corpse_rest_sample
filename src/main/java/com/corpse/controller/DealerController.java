package com.corpse.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.Dealer;
import com.corpse.model.ResponseUDI;
import com.corpse.service.DealerService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/dealer")
@Secured(Common.ROLE_OWNER)
public class DealerController {

	@Autowired
	private DealerService dealerService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Dealer> getAll(Principal principal) {
		return dealerService.getAll(Common.getUser(principal));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Dealer> getByKey(Principal principal, @PathVariable("id") long id) {
		return dealerService.getByKey(id, Common.getUser(principal));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(
			@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("contractor") Long contractor,
			@RequestParam("storage") Long storage) {
		Dealer dealer = new Dealer();
		dealer.setId(id);
		dealer.setName(name);
		dealer.setContractor(contractor);
		dealer.setStorage(storage);
		return dealerService.updateByKey(dealer);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return dealerService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(@RequestParam("name") String name,
			@RequestParam("contractor") Long contractor,
			@RequestParam(value = "storage", required = false) Long storage) {
		Dealer dealer = new Dealer();
		dealer.setName(name);
		dealer.setContractor(contractor);
		dealer.setStorage(storage);
		return dealerService.insert(dealer);
	}
}
