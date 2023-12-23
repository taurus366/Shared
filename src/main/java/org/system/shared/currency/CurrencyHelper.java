package org.system.shared.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import org.springframework.stereotype.Component;
import org.system.shared.model.entity.CurrencyEntity;
import org.system.shared.model.service.CurrencyService;

@Component
public class CurrencyHelper {

    private final CurrencyService currencyService;
    public CurrencyHelper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    public ComboBox<CurrencyEntity> getCurrencyActiveSelectBox(String title) {
        ComboBox<CurrencyEntity> select = new ComboBox<>(title);
        select.setItems(this.currencyService.getAllActiveCurrencies());
        select.setItemLabelGenerator(CurrencyEntity::getTitle);
        return select;
    }
}
