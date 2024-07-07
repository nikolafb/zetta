package zetta.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import zetta.domain.dto.ExchangeRateDto;
import zetta.domain.dto.client.ExchangeRateResponseDto;
import zetta.domain.mapper.CurrencyMapper;
import zetta.httpclient.HttpClientExchangeRateService;
import zetta.httpclient.impl.HttpClientExchangeRateServiceImpl;
import zetta.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger log = LogManager.getLogger(HttpClientExchangeRateServiceImpl.class);

    private final HttpClientExchangeRateService httpClientExchangeRateService;

    private final CurrencyMapper currencyMapper;

    public CurrencyServiceImpl(HttpClientExchangeRateService httpClientExchangeRateService,
                               CurrencyMapper currencyMapper) {
        this.httpClientExchangeRateService = httpClientExchangeRateService;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public ExchangeRateDto getExchangeRate(String sourceCurrency, String targetCurrency) {
        ExchangeRateResponseDto exchangeRateDto = httpClientExchangeRateService.getExchangeRates(sourceCurrency);
        log.info("Client response result:{}", exchangeRateDto.getResult());

        Double rateValue = exchangeRateDto.getConversionRates().get(targetCurrency.toUpperCase());


        return currencyMapper.mapToExchangeRateDto(sourceCurrency, targetCurrency, rateValue);
    }
}
