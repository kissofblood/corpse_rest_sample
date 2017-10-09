package com.corpse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.Price;
import com.corpse.model.ResponseUDI;
import com.corpse.model.User;
import com.corpse.repository.PriceRepository;

@Service
@Transactional
public class PriceService {

	@Autowired
	private PriceRepository priceRepository;

	private Long getDealer(Long dealer, User user) {
		if(user.getIsAdmin()) {
			if(dealer == null) {
				return user.getDealer();
			}
			return dealer;
		}
		return user.getDealer();
	}

	public List<Price> getAll() {
		List<Price> list = new ArrayList<>();
		priceRepository.findAll().forEach(list::add);
		return list;
	}

	public List<Price> getByKey(long id) {
		List<Price> list = new ArrayList<>();
		Price price = priceRepository.findOne(id);
		if(price != null) {
			list.add(price);
		}
		return list;
	}

	public ResponseUDI<Long> updateByKey(Price price, User user) {
		price.setDealer(getDealer(price.getDealer(), user));

		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setId(price.getDealer());

		if(priceRepository.exists(price.getDealer())) {
			priceRepository.save(price);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}
		resp.setOperation(ResponseUDI.TypeOperation.UPDATE);
		return resp;
	}

	public ResponseUDI<Long> deleteByKey(Long dealer, User user) {
		Long d = getDealer(dealer, user);

		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setId(d);
		if(priceRepository.exists(d)) {
			priceRepository.delete(d);
			resp.setCount(1);
		}
		else {
			resp.setCount(0);
		}

		resp.setOperation(ResponseUDI.TypeOperation.DELETE);
		return resp;
	}

	public ResponseUDI<Long> insert(Price price, User user) {
		price.setDealer(getDealer(price.getDealer(), user));
		priceRepository.save(price);

		ResponseUDI<Long> resp = new ResponseUDI<>();
		resp.setOperation(ResponseUDI.TypeOperation.INSERT);
		resp.setCount(1);
		resp.setId(price.getDealer());
		return resp;
	}
}
