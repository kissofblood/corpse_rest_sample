package com.corpse.controller.document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corpse.model.document.CommissionReport;
import com.corpse.service.document.CommissionReportService;
import com.corpse.util.Common;

@Controller
@RequestMapping("/api/commissionreport")
@Secured(Common.ROLE_OWNER)
public class CommissionReportController extends ControllerDocument<CommissionReport> {

	@Autowired
	public void setCommissionReportService(CommissionReportService commissionReportService) {
		service = commissionReportService;
	}
}
