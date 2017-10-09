package com.corpse.service.document;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.document.CommissionReport;
import com.corpse.repository.document.CommissionReportRepository;

@Service
@Transactional
public class CommissionReportService extends ServiceDocument<CommissionReport> {
	
	public CommissionReportService() {
		clazz = CommissionReport.class;
	}
	
	@Autowired
	public void setCommissionReportRepository(CommissionReportRepository commissionReportRepository) {
		repository = commissionReportRepository;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(CommissionReport document) {
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
