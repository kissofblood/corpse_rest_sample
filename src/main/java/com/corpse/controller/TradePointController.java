package com.corpse.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpse.model.ResponseUDI;
import com.corpse.model.TradePoint;
import com.corpse.service.TradePointService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/tradepoint")
@Secured(Common.ROLE_OWNER)
public class TradePointController {

	@Autowired
	private TradePointService tradePointService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TradePoint> getAll(Principal principal, @RequestParam Map<String, String> params) {
		return tradePointService.getAll(params, Common.getUser(principal));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<TradePoint> getByKey(Principal principal, @PathVariable("id") long id) {
		return tradePointService.getByKey(id, Common.getUser(principal));
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> updateByKey(@RequestParam("id") long id,
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("type") Long type,
			@RequestParam("dealer") Long dealer,
			@RequestParam("trader") String trader,
			@RequestParam("storage") Long storage,
			@RequestParam("contractor") Long contractor,
			@RequestParam("creaditLimitMoney") Double creaditLimitMoney,
			@RequestParam("creaditLimitDay") Double creaditLimitDay,
			@RequestParam("paymentType") String paymentType,
			@RequestParam("phone") String phone,
			@RequestParam("phone2") String phone2,
			@RequestParam("visitPeriod") Integer visitPeriod,
			@RequestParam(value = "hashSync", required = false) String hashSync) {
		TradePoint tradePoint = new TradePoint();
		tradePoint.setId(id);
		tradePoint.setName(name);
		tradePoint.setAddress(address);
		tradePoint.setType(type);
		tradePoint.setDealer(dealer);
		tradePoint.setTrader(trader);
		tradePoint.setContractor(contractor);
		tradePoint.setCreaditLimitMoney(creaditLimitMoney);
		tradePoint.setCreaditLimitDay(creaditLimitDay);
		tradePoint.setStorage(storage);
		tradePoint.setPaymentType(paymentType);
		tradePoint.setPhone(phone);
		tradePoint.setPhone2(phone2);
		tradePoint.setVisitPeriod(visitPeriod);
		tradePoint.setHashSync(hashSync);
		return tradePointService.updateByKey(tradePoint);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseUDI<Long> deleteByKey(@PathVariable("id") long id) {
		return tradePointService.deleteByKey(id);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> deleteByKeys(@RequestBody List<Long> ids) {
		return tradePointService.deleteByKeys(ids);
	}

	@RequestMapping(value = "/deleteforce", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> deleteForceByKeys(@RequestBody List<Long> ids) {
		return tradePointService.deleteForceByKeys(ids);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public ResponseUDI<Long> insert(@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("type") Long type,
			@RequestParam("dealer") Long dealer,
			@RequestParam("trader") String trader,
			@RequestParam(value = "storage", required = false) Long storage,
			@RequestParam("contractor") Long contractor,
			@RequestParam("creaditLimitMoney") Double creaditLimitMoney,
			@RequestParam("creaditLimitDay") Double creaditLimitDay,
			@RequestParam("paymentType") String paymentType,
			@RequestParam("phone") String phone,
			@RequestParam("phone2") String phone2,
			@RequestParam("visitPeriod") Integer visitPeriod,
			@RequestParam(value = "hashSync", required = false) String hashSync) {
		TradePoint tradePoint = new TradePoint();
		tradePoint.setName(name);
		tradePoint.setAddress(address);
		tradePoint.setType(type);
		tradePoint.setDealer(dealer);
		tradePoint.setTrader(trader);
		tradePoint.setContractor(contractor);
		tradePoint.setCreaditLimitMoney(creaditLimitMoney);
		tradePoint.setCreaditLimitDay(creaditLimitDay);
		tradePoint.setStorage(storage);
		tradePoint.setPaymentType(paymentType);
		tradePoint.setPhone(phone);
		tradePoint.setPhone2(phone2);
		tradePoint.setVisitPeriod(visitPeriod);
		tradePoint.setHashSync(hashSync);
		return tradePointService.insert(tradePoint);
	}

	@RequestMapping(value = "/insert/object", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<Long>> insertObject(@RequestBody List<TradePoint> tradePoints) {
		return tradePointService.insertObject(tradePoints);
	}

	@RequestMapping(value = "/update/object", method = RequestMethod.POST)
	@ResponseBody
	public List<ResponseUDI<Long>> updateObject(@RequestBody List<TradePoint> tradePoints) {
		return tradePointService.updateObject(tradePoints);
	}
}
