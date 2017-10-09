package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.PurchaseReturn;
import com.corpse.service.document.PurchaseReturnService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/purchasereturn")
@Secured(Common.ROLE_OWNER)
public class PurchaseReturnController extends ControllerDocument<PurchaseReturn> {

	@Autowired
	public void setPurchaseReturnService(PurchaseReturnService purchaseReturnService) {
		service = purchaseReturnService;
	}
}
