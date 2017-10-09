package com.corpse.repository.document;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.corpse.model.document.Payment;

public interface PaymentRepository extends RepositoryDocument<Payment> {

	@Override
	@Modifying
	@Query("DELETE FROM Payment WHERE id in(:ids)")
	int deleteByIdIn(@Param("ids") List<Long> ids);
}
