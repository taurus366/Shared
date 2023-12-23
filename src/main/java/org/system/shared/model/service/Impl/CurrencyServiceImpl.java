package org.system.shared.model.service.Impl;

import org.springframework.stereotype.Service;
import org.system.shared.model.entity.CurrencyEntity;
import org.system.shared.model.repository.CurrencyRepository;
import org.system.shared.model.service.CurrencyService;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }


    @Override
    public List<CurrencyEntity> getAllActiveCurrencies() {
        return this.currencyRepository.findAllByActive(true);
    }

    @Override
    public Optional<CurrencyEntity> getCurrencyById(Long id) {
        return this.currencyRepository.findById(id);
    }

    @Override
    public void SaveNewCurrency(CurrencyEntity currency) {
        this.currencyRepository.save(currency);
    }
}
