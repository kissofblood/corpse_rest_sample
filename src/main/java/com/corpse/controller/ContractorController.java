package com.corpse.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.Contractor;
import com.corpse.model.ResponseUDI;
import com.corpse.model.User;
import com.corpse.service.ContractorService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/contractor")
@Secured(Common.ROLE_OWNER)
public class ContractorController {

	@Autowired
	private ContractorService contractorService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Contractor> getAll(Principal principal) {
		return contractorService.getAll(Common.getUser(principal));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Contractor> getByKey(Principal principal, @PathVariable("id") long id) {
		return contractorService.getByKey(id, Common.getUser(principal));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(Principal principal,
			@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("address") String address,
			@RequestParam(value = "dealer", required = false) Long dealer,
			@RequestParam(value = "hashSync", required = false) String hashSync) {
		User user = Common.getUser(principal);
		Contractor contractor = new Contractor();
		contractor.setId(id);
		contractor.setName(name);
		contractor.setPhone(phone);
		contractor.setEmail(email);
		contractor.setAddress(address);
		contractor.setDealer(dealer == null ?
			user.getDealer() : dealer
		);
		contractor.setTrader(user.getUsername());
		return contractorService.updateByKey(contractor);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return contractorService.deleteByKey(id);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(Principal principal,
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("address") String address,
			@RequestParam(value = "dealer", required = false) Long dealer,
			@RequestParam(value = "hashSync", required = false) String hashSync) {
		User user = Common.getUser(principal);
		Contractor contractor = new Contractor();
		contractor.setName(name);
		contractor.setPhone(phone);
		contractor.setEmail(email);
		contractor.setAddress(address);
		contractor.setDealer(dealer == null ?
			user.getDealer() : dealer
		);
		contractor.setTrader(user.getUsername());
		return contractorService.insert(contractor);
	}

	@RequestMapping(value = "/update/object", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<Long>> updateObject(Principal principal,
			@RequestBody List<Contractor> contractors) {
		return contractorService.updateObject(Common.getUser(principal), contractors);
	}

	@RequestMapping(value = "/insert/object", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<Long>> insertObject(Principal principal,
			@RequestBody List<Contractor> contractors) {
			return contractorService.insertObject(Common.getUser(principal), contractors);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> deleteByKeys(@RequestBody List<Long> ids) {
		return contractorService.deleteByKeys(ids);
	}
}
