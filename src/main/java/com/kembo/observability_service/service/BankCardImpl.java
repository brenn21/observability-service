package com.kembo.observability_service.service;


import com.kembo.observability_service.exception.BankCardException;
import com.kembo.observability_service.model.BankCard;

import java.util.List;
import java.util.Optional;

public interface BankCardImpl {

    List<BankCard> getBankCards();
    Optional<BankCard> getBankCardById(Long id) throws BankCardException;
    BankCard createBankCard(BankCard bankCard);
    BankCard updateBankCard(Long id, BankCard bankCard) throws BankCardException;
    String deleteBankCard(Long id) throws BankCardException;
}
