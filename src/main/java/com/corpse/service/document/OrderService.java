package com.corpse.service.document;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corpse.model.User;
import com.corpse.model.document.Order;
import com.corpse.repository.document.OrderRepository;

@Service
@Transactional
public class OrderService extends ServiceDocument<Order> {

	public OrderService() {
		clazz = Order.class;
	}
	
	@Autowired
	public void setOrderRepository(OrderRepository orderRepository) {
		repository = orderRepository;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected void checkValue(Order document) {
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

	@Override
	protected void prepareFilter(Map<String, String> params, List<String> nameArgs,
			Map<String, Object> args, StringBuilder query, long id, User user) {
		super.prepareFilter(params, nameArgs, args, query, id, user);
		
		for(Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if(key.equals("isdefermentpayment")) {
				if(Boolean.parseBoolean(value)) {
					nameArgs.add("D.maturity IS NOT NULL");
		    	}
		    	else {
		    		nameArgs.add("D.maturity IS NULL");
		    	}
		    }

		    if(key.equals("maturityfrom")) {
		    	nameArgs.add("D.maturity>=:maturityFromOrder");
		    	Calendar c = Calendar.getInstance();
		    	c.setTimeInMillis(Long.parseLong(value));
		    	args.put("maturityFromOrder", c);
		    }
		    
		    if(key.equals("maturityto")) {
		    	nameArgs.add("D.maturity<=:maturityToOrder");
		    	Calendar c = Calendar.getInstance();
		    	c.setTimeInMillis(Long.parseLong(value));
		    	args.put("maturityToOrder", c);
		    }
		}
	}
}
