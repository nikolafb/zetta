package zetta.service.impl;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zetta.domain.dto.ConversionHistoryDto;
import zetta.domain.dto.client.ExchangeRateResponseDto;
import zetta.domain.dto.criteria.ConversionHistoryCriteriaDto;
import zetta.domain.dto.request.CurrencyCovertRequestDto;
import zetta.domain.dto.response.CurrencyCovertResponseDto;
import zetta.domain.entity.CurrencyConversion;
import zetta.domain.mapper.CurrencyMapper;
import zetta.exception.EntityNotFoundException;
import zetta.httpclient.HttpClientExchangeRateService;
import zetta.httpclient.impl.HttpClientExchangeRateServiceImpl;
import zetta.repository.CurrencyConversionRepository;
import zetta.util.locale.LocaleUtil;

import static org.mockito.ArgumentMatchers.any;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CurrencyServiceImplTest {

    @Mock
    private HttpClientExchangeRateService httpClientExchangeRateService;

    @Mock
    private CurrencyConversionRepository currencyConversionRepository;

    @Mock
    private CurrencyMapper currencyMapper;

    @Mock
    private LocaleUtil localeUtil;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    MockedStatic<LocaleUtil> mockedStatic;


    @BeforeEach
    void setUpTwo() {
        mockedStatic = Mockito.mockStatic(LocaleUtil.class);
    }


    @Test
    void getExchangeRateShouldReturnCorrectRateWhenValidCurrenciesProvided() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        Double expectedRate = 0.85;

        ExchangeRateResponseDto exchangeRateResponseDto = new ExchangeRateResponseDto();
        Map<String, Double> conversionRates = new HashMap<>();
        conversionRates.put(targetCurrency, expectedRate);
        exchangeRateResponseDto.setConversionRates(conversionRates);

        when(httpClientExchangeRateService.getExchangeRates(sourceCurrency)).thenReturn(exchangeRateResponseDto);

        Double actualRate = currencyService.getExchangeRate(sourceCurrency, targetCurrency);

        assertEquals(expectedRate, actualRate);
    }

    @Test
    void getExchangeRateShouldThrowExceptionWhenInvalidSourceCurrencyProvided() {
        String sourceCurrency = "INVALID";
        String targetCurrency = "EUR";

        when(httpClientExchangeRateService.getExchangeRates(sourceCurrency)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> currencyService.getExchangeRate(sourceCurrency, targetCurrency));
    }

    @Test
    void getExchangeRateShouldThrowExceptionWhenInvalidTargetCurrencyProvided() {
        String sourceCurrency = "USD";
        String targetCurrency = "INVALID";
        Double expectedRate = 0.85;

        ExchangeRateResponseDto exchangeRateResponseDto = new ExchangeRateResponseDto();
        Map<String, Double> conversionRates = new HashMap<>();
        exchangeRateResponseDto.setConversionRates(conversionRates);

        when(httpClientExchangeRateService.getExchangeRates(sourceCurrency)).thenReturn(exchangeRateResponseDto);

        assertThrows(EntityNotFoundException.class, () -> currencyService.getExchangeRate(sourceCurrency, targetCurrency));
    }




    @Test
    void convertCurrencyReturnsCorrectResponseWhenValidRequest() {
        CurrencyCovertRequestDto requestDto = new CurrencyCovertRequestDto();
        requestDto.setSourceCurrency("USD");
        requestDto.setTargetCurrency("EUR");
        requestDto.setAmount(100.0);

        ExchangeRateResponseDto exchangeRateResponseDto = new ExchangeRateResponseDto();
        Map<String, Double> conversionRates = new HashMap<>();
        conversionRates.put("EUR", 0.85);
        exchangeRateResponseDto.setConversionRates(conversionRates);

        CurrencyConversion currencyConversion = new CurrencyConversion();
        CurrencyCovertResponseDto expectedResponseDto = new CurrencyCovertResponseDto();
        expectedResponseDto.setConvertedAmount(85.0);

        when(httpClientExchangeRateService.getExchangeRates(any())).thenReturn(exchangeRateResponseDto);
        when(currencyMapper.mapCurrencyCovertRequestDtoToCurrencyConversion(any(), any(), any(), any())).thenReturn(currencyConversion);
        when(currencyConversionRepository.save(any())).thenReturn(currencyConversion);
        when(currencyMapper.mapCurrencyConversionToCurrencyCovertResponseDto(any())).thenReturn(expectedResponseDto);

        CurrencyCovertResponseDto responseDto = currencyService.convertCurrency(requestDto);

        assertEquals(expectedResponseDto.getConvertedAmount(), responseDto.getConvertedAmount());
    }


    @Test
    void convertCurrencyShouldThrowExceptionWhenAmountExceedsMaxValue() {
        CurrencyCovertRequestDto requestDto = new CurrencyCovertRequestDto();
        requestDto.setSourceCurrency("USD");
        requestDto.setTargetCurrency("EUR");
        requestDto.setAmount(Double.MAX_VALUE);

        ExchangeRateResponseDto exchangeRateResponseDto = new ExchangeRateResponseDto();
        Map<String, Double> conversionRates = new HashMap<>();
        conversionRates.put("EUR", 2.0);
        exchangeRateResponseDto.setConversionRates(conversionRates);

        when(httpClientExchangeRateService.getExchangeRates(any())).thenReturn(exchangeRateResponseDto);


        when(LocaleUtil.getLocaleMassage("exchange.amount.covert")).thenReturn("exchange.amount.covert");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            currencyService.convertCurrency(requestDto);
        });

        assertEquals("exchange.amount.covert", exception.getMessage());
    }

    @Test
    void retrieveConversionHistoryReturnsCorrectResponseWhenValidRequest() {
        ConversionHistoryCriteriaDto criteriaDto = new ConversionHistoryCriteriaDto();
        Pageable pageable = PageRequest.of(0, 5);
        CurrencyConversion currencyConversion = new CurrencyConversion();
        List<CurrencyConversion> currencyConversions = List.of(currencyConversion);
        Page<CurrencyConversion> page = new PageImpl<>(currencyConversions);

        when(currencyConversionRepository.findCurrencyConversion(criteriaDto, pageable)).thenReturn(page);

        ConversionHistoryDto conversionHistoryDto = new ConversionHistoryDto();
        when(currencyMapper.mapConversionHistoryToConversionHistoryDto(currencyConversion)).thenReturn(conversionHistoryDto);

        Page<ConversionHistoryDto> result = currencyService.retrieveConversionHistory(criteriaDto, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(conversionHistoryDto, result.getContent().get(0));
    }

    @Test
    void retrieveConversionHistoryReturnsEmptyWhenNoConversions() {
        ConversionHistoryCriteriaDto criteriaDto = new ConversionHistoryCriteriaDto();
        Pageable pageable = PageRequest.of(0, 5);
        Page<CurrencyConversion> page = Page.empty();

        when(currencyConversionRepository.findCurrencyConversion(criteriaDto, pageable)).thenReturn(page);

        Page<ConversionHistoryDto> result = currencyService.retrieveConversionHistory(criteriaDto, pageable);

        assertTrue(result.getContent().isEmpty());
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

}