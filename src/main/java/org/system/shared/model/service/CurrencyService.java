package org.system.shared.model.service;


import org.system.shared.model.entity.CurrencyEntity;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {

    List<CurrencyEntity> getAllActiveCurrencies();

    Optional<CurrencyEntity> getCurrencyById(Long id);

    void SaveNewCurrency(CurrencyEntity currency);

}
