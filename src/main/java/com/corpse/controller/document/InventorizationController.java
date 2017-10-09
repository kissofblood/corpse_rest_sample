package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.Inventorization;
import com.corpse.service.document.InventorizationService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/inventorization")
@Secured(Common.ROLE_OWNER)
public class InventorizationController extends ControllerDocument<Inventorization> {

	@Autowired
	public void setInventorizationService(InventorizationService inventorizationService) {
		service = inventorizationService;
	}
}
