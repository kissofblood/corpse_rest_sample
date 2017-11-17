package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.InventorizationDealer;
import com.corpse.service.document.InventorizationDealerService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/inventorizationdealer")
@Secured(Common.ROLE_OWNER)
public class InventorizationDealerController extends ControllerDocument<InventorizationDealer> {

	@Autowired
	public void setInventorizationDealerService(InventorizationDealerService inventorizationDealerService) {
		service = inventorizationDealerService;
	}

}
