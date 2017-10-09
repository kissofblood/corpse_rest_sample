package com.corpse.service.document;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.document.InventorizationDealer;
import com.corpse.repository.document.InventorizationDealerRepository;

@Service
@Transactional
public class InventorizationDealerService extends ServiceDocument<InventorizationDealer> {

	public InventorizationDealerService() {
		clazz = InventorizationDealer.class;
	}
	
	@Autowired
	public void setInventorizationDealerRepository(InventorizationDealerRepository inventorizationDealerRepository) {
		repository = inventorizationDealerRepository;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(InventorizationDealer document) {
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
