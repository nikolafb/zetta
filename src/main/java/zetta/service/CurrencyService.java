package zetta.service;

import zetta.domain.dto.ExchangeRateDto;

public interface CurrencyService {

    public ExchangeRateDto getExchangeRate(String sourceCurrency, String targetCurrency);
}
