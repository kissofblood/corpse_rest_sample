package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.Order;
import com.corpse.service.document.OrderService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/order")
@Secured(Common.ROLE_OWNER)
public class OrderController extends ControllerDocument<Order> {

	@Autowired
	public void setOrderService(OrderService orderService) {
		service = orderService;
	}
}
