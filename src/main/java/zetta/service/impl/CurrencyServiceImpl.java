package zetta.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zetta.domain.dto.client.ExchangeRateResponseDto;
import zetta.domain.dto.request.CurrencyCovertRequestDto;
import zetta.domain.dto.response.CurrencyCovertResponseDto;
import zetta.domain.entity.CurrencyConversion;
import zetta.domain.mapper.CurrencyMapper;
import zetta.httpclient.HttpClientExchangeRateService;
import zetta.httpclient.impl.HttpClientExchangeRateServiceImpl;
import zetta.repository.CurrencyConversionRepository;
import zetta.service.CurrencyService;
import zetta.util.locale.LocaleUtil;

import java.util.Objects;
import java.util.UUID;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger log = LogManager.getLogger(HttpClientExchangeRateServiceImpl.class);

    private final HttpClientExchangeRateService httpClientExchangeRateService;

    private final CurrencyConversionRepository currencyConversionRepository;

    private final CurrencyMapper currencyMapper;

    public CurrencyServiceImpl(HttpClientExchangeRateService httpClientExchangeRateService,
                               CurrencyConversionRepository currencyConversionRepository,
                               CurrencyMapper currencyMapper) {
        this.httpClientExchangeRateService = httpClientExchangeRateService;
        this.currencyConversionRepository = currencyConversionRepository;
        this.currencyMapper = currencyMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getExchangeRate(String sourceCurrency, String targetCurrency) {
        ExchangeRateResponseDto exchangeRateDto = exchangeRateClient(sourceCurrency);

        return exchangeRateDto.getConversionRates().get(targetCurrency.toUpperCase());
    }

    @Override
    @Transactional
    public CurrencyCovertResponseDto convertCurrency(CurrencyCovertRequestDto currencyCovertRequestDto) {
        Double rateValue = getExchangeRate(currencyCovertRequestDto.getSourceCurrency(), currencyCovertRequestDto.getTargetCurrency());

        //make the conversion
        Double convertedAmount = convertAmount(currencyCovertRequestDto.getAmount(), rateValue);
        log.info("convertCurrency: convertedAmount:{} with rate: {}", convertedAmount, rateValue);

        CurrencyConversion currencyConversion = currencyMapper.mapCurrencyCovertRequestDtoToCurrencyConversion(currencyCovertRequestDto, convertedAmount,
                rateValue, generateTransactionId());

        currencyConversion = currencyConversionRepository.save(currencyConversion);
        log.info("CurrencyConversion saved with transactionId:{} ", currencyConversion.getTransactionId());

        return currencyMapper.mapCurrencyConversionToCurrencyCovertResponseDto(currencyConversion);
    }

    private Double convertAmount(final Double amount, final Double rateValue) {
        Double convertedAmount = amount * rateValue;

        // Validate if the convertedAmount is not more than it can be stored in the database and in memory
        if (convertedAmount > Double.MAX_VALUE || Double.isInfinite(convertedAmount) || convertedAmount.isNaN()) {
            throw new IllegalArgumentException(LocaleUtil.getLocaleMassage("exchange.amount.covert"));
        }

        return convertedAmount;
    }

    private ExchangeRateResponseDto exchangeRateClient(final String sourceCurrency) {
        ExchangeRateResponseDto response = httpClientExchangeRateService.getExchangeRates(sourceCurrency);
        if (!Objects.nonNull(response)){
            throw new RuntimeException(LocaleUtil.getLocaleMassage("exchange.client"));
        }

        log.info("Client response result:{}", response.getResult());
        return response;
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

}
