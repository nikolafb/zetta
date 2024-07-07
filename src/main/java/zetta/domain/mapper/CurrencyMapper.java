package zetta.domain.mapper;

import org.springframework.stereotype.Component;
import zetta.domain.dto.ExchangeRateDto;

@Component
public class CurrencyMapper {

    public ExchangeRateDto mapToExchangeRateDto(String sourceCurrency, String targetCurrency, Double exchangeRate) {
        return new ExchangeRateDto()
                .sourceCurrency(sourceCurrency)
                .targetCurrency(targetCurrency)
                .exchangeRate(exchangeRate);
    }
}
