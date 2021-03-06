package com.corpse.service.document;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.document.Checklist;
import com.corpse.repository.document.ChecklistRepository;

@Service
@Transactional
public class ChecklistService extends ServiceDocument<Checklist> {

	public ChecklistService() {
		clazz = Checklist.class;
	}

	@Autowired
	public void setChecklistRepository(ChecklistRepository checklistRepository) {
		repository = checklistRepository;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(Checklist document) {
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
