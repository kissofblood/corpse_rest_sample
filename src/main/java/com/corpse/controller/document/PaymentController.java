package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.Payment;
import com.corpse.service.document.PaymentService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/payment")
@Secured(Common.ROLE_OWNER)
public class PaymentController extends ControllerDocument<Payment> {

	@Autowired
	public void setPaymentService(PaymentService paymentService) {
		service = paymentService;
	}
}
