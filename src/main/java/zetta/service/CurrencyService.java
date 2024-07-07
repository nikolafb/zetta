package zetta.service;

import zetta.domain.dto.request.CurrencyCovertRequestDto;
import zetta.domain.dto.response.CurrencyCovertResponseDto;

public interface CurrencyService {

    public Double getExchangeRate(String sourceCurrency, String targetCurrency);

    public CurrencyCovertResponseDto convertCurrency(CurrencyCovertRequestDto currencyCovertRequestDto);
}
