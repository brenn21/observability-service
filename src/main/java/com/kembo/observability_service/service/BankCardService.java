package com.kembo.observability_service.service;

import com.kembo.observability_service.exception.BankCardException;
import com.kembo.observability_service.model.BankCard;
import com.kembo.observability_service.repository.BankCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankCardService implements BankCardImpl{

    private final BankCardRepository bankCardRepository;

    public BankCardService(BankCardRepository bankCardRepository) {
        this.bankCardRepository = bankCardRepository;
    }

    @Override
    public List<BankCard> getBankCards() {
        return bankCardRepository.findAll();
    }

    @Override
    public Optional<BankCard> getBankCardById(Long id) throws BankCardException {
        Optional<BankCard> optionalBankCard = bankCardRepository.findById(id);
        if (optionalBankCard.isPresent()) {
            return bankCardRepository.findById(id);
        }
        throw new BankCardException("Bank Card not found.");
    }

    @Override
    public BankCard createBankCard(BankCard bankCard) {
        return bankCardRepository.save(bankCard);
    }

    @Override
    public BankCard updateBankCard(Long id, BankCard bankCard) throws BankCardException {
        Optional<BankCard> optionalBankCard = bankCardRepository.findById(id);
        if (optionalBankCard.isPresent()) {
            optionalBankCard.get().setName(bankCard.getName());
            optionalBankCard.get().setCardNumber(bankCard.getCardNumber());
            optionalBankCard.get().setMoney(bankCard.getMoney());

            return bankCardRepository.save(optionalBankCard.get());
        }
        else throw new BankCardException("Bank Card not found.");
    }

    @Override
    public String deleteBankCard(Long id) throws BankCardException {
        bankCardRepository.findById(id);
        if (bankCardRepository.existsById(id)) {
            bankCardRepository.deleteById(id);
           return String.format("Bank Card with id: %s was deleted.", id);
        }
        throw new BankCardException("Not Found This Bank Card");
    }
}
