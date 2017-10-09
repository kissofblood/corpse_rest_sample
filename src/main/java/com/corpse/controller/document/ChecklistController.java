package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.Checklist;
import com.corpse.service.document.ChecklistService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/checklist")
@Secured(Common.ROLE_OWNER)
public class ChecklistController extends ControllerDocument<Checklist> {

	@Autowired
	public void setChecklistService(ChecklistService checklistService) {
		service = checklistService;
	}
}
