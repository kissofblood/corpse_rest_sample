package com.corpse.service.document;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.document.DemandDealer;
import com.corpse.repository.document.DemandDealerRepository;

@Service
@Transactional
public class DemandDealerService extends ServiceDocument<DemandDealer> {
	
	public DemandDealerService() {
		clazz = DemandDealer.class;
	}
	
	@Autowired
	public void setDemandDealerRepository(DemandDealerRepository dealerRepository) {
		repository = dealerRepository;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(DemandDealer document) {
		if(document.getApplicable()) {
			if(document.getOwner() == null) {
				throw new RuntimeException("owner is null");
			}
			else if(document.getOwner().isEmpty()) {
				throw new RuntimeException("owner is emtpy");
			}
		}
	}
}

