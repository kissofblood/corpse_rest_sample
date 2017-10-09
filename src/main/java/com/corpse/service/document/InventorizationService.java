package com.corpse.service.document;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.document.Inventorization;
import com.corpse.repository.document.InventorizationRepository;

@Service
@Transactional
public class InventorizationService extends ServiceDocument<Inventorization> {

	public InventorizationService() {
		clazz = Inventorization.class;
	}
	
	@Autowired
	public void setInventorizationRepository(InventorizationRepository inventorizationRepository) {
		repository = inventorizationRepository;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(Inventorization document) {
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
