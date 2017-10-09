package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.DemandDealer;
import com.corpse.service.document.DemandDealerService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/demanddealer")
@Secured(Common.ROLE_OWNER)
public class DemandDealerController extends ControllerDocument<DemandDealer> {

	@Autowired
	public void setDemandDealerService(DemandDealerService demandDealerService) {
		service = demandDealerService;
	}
}
