package com.corpse.service.document;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.document.Demand;
import com.corpse.repository.document.DemandRepository;

@Service
@Transactional
public class DemandService extends ServiceDocument<Demand> {

	public DemandService() {
		clazz = Demand.class;
	}
	
	@Autowired
	public void setDemandRepository(DemandRepository demandRepository) {
		repository = demandRepository;
				
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(Demand document) {
		if(document.getApplicable()) {
			if(document.getOwner() == null) {
				throw new RuntimeException("owner is null");
			}
			else if(document.getOwner().isEmpty()) {
				throw new RuntimeException("owner is emtpy");
			}
			
			if(document.getTradePoint() == null) {
				throw new RuntimeException("tradePoint is null");
			}
		}
	}
}
