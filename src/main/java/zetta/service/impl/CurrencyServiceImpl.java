package zetta.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zetta.domain.dto.ConversionHistoryDto;
import zetta.domain.dto.client.ExchangeRateResponseDto;
import zetta.domain.dto.criteria.ConversionHistoryCriteriaDto;
import zetta.domain.dto.request.CurrencyCovertRequestDto;
import zetta.domain.dto.response.CurrencyCovertResponseDto;
import zetta.domain.entity.CurrencyConversion;
import zetta.domain.mapper.CurrencyMapper;
import zetta.exception.BusinessExceptionFactory;
import zetta.httpclient.HttpClientExchangeRateService;
import zetta.repository.CurrencyConversionRepository;
import zetta.service.CurrencyService;
import zetta.util.locale.LocaleUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger log = LogManager.getLogger(CurrencyServiceImpl.class);

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
    @Cacheable(value = "exchangeRates", key = "#sourceCurrency + '-' + #targetCurrency")
    public Double getExchangeRate(String sourceCurrency, String targetCurrency) {
        ExchangeRateResponseDto exchangeRateDto = exchangeRateClient(sourceCurrency);

        Double rateValue = exchangeRateDto.getConversionRates().get(targetCurrency.toUpperCase());
        if (!Objects.nonNull(rateValue)) {
            log.error("Target currency not found: {}", targetCurrency);
            throw BusinessExceptionFactory.entityNotFoundException("Not Found", LocaleUtil.getLocaleMassage("exchange.target.not.found"));
        }

        return rateValue;
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

    @Override
    @Transactional(readOnly = true)
    public Page<ConversionHistoryDto> retrieveConversionHistory(ConversionHistoryCriteriaDto conversionHistoryCriteriaDto, Pageable pageable) {
        Page<CurrencyConversion> currencyConversions = currencyConversionRepository.findCurrencyConversion(conversionHistoryCriteriaDto, pageable);

        List<ConversionHistoryDto> conversions = currencyConversions.getContent().stream()
                .map(currencyMapper::mapConversionHistoryToConversionHistoryDto)
                .toList();

        return new PageImpl<>(conversions, pageable, currencyConversions.getTotalElements());
    }

    private Double convertAmount(final Double amount, final Double rateValue) {
        Double convertedAmount = amount * rateValue;

        // Validate if the convertedAmount is not more than it can be stored in the database and in memory
        if (convertedAmount > Double.MAX_VALUE || Double.isInfinite(convertedAmount) || convertedAmount.isNaN()) {
            log.fatal("Converted amount is too large to be stored in the database");
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
