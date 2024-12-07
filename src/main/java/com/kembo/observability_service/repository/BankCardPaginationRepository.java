package com.kembo.observability_service.repository;

import com.kembo.observability_service.model.BankCard;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardPaginationRepository extends PagingAndSortingRepository<BankCard, Long> {
}
