package com.corpse.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.corpse.model.document.CommissionReport;

public interface CommissionReportRepository extends RepositoryDocument<CommissionReport> {

	@Override
	@Modifying
	@Query("DELETE FROM CommissionReport WHERE id in(:ids)")
	int deleteByIdIn(@Param("ids") List<Long> ids);
}
