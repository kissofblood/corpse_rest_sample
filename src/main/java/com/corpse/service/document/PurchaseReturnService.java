package com.corpse.service.document;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.document.PurchaseReturn;
import com.corpse.repository.document.PurchaseReturnRepository;

@Service
@Transactional
public class PurchaseReturnService extends ServiceDocument<PurchaseReturn> {

	public PurchaseReturnService() {
		clazz = PurchaseReturn.class;
	}
	
	@Autowired
	public void setPurchaseReturnRepository(PurchaseReturnRepository purchaseReturnRepository) {
		repository = purchaseReturnRepository;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(PurchaseReturn document) {
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
