package zetta.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zetta.domain.dto.ConversionHistoryDto;
import zetta.domain.dto.criteria.ConversionHistoryCriteriaDto;
import zetta.domain.dto.request.CurrencyCovertRequestDto;
import zetta.domain.dto.response.CurrencyCovertResponseDto;

public interface CurrencyService {

    public Double getExchangeRate(String sourceCurrency, String targetCurrency);

    public CurrencyCovertResponseDto convertCurrency(CurrencyCovertRequestDto currencyCovertRequestDto);

    public Page<ConversionHistoryDto> retrieveConversionHistory(ConversionHistoryCriteriaDto conversionHistoryCriteriaDto, Pageable pageable);
}
