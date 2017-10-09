package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.Demand;
import com.corpse.service.document.DemandService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/demand")
@Secured(Common.ROLE_OWNER)
public class DemandController extends ControllerDocument<Demand> {

	@Autowired
	public void setDemandService(DemandService demandService) {
		service = demandService;
	}
}
