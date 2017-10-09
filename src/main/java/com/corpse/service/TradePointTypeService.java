package com.corpse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.TradePointType;
import com.corpse.repository.TradePointTypeRepository;

@Service
@Transactional
public class TradePointTypeService extends ServiceIdName<TradePointType> {

	@Autowired
	public void setTradePointTypeRepository(TradePointTypeRepository tradePointTypeRepository) {
		repository = tradePointTypeRepository;
	}
}
