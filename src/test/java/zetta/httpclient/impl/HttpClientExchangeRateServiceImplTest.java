package zetta.httpclient.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import zetta.config.properties.Properties;
import zetta.domain.dto.client.ExchangeRateResponseDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class HttpClientExchangeRateServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Properties.ExchangeRateApi exchangeRateApi;

    @InjectMocks
    private HttpClientExchangeRateServiceImpl httpClientExchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExchangeRatesReturnsCorrectResponseWhenValidCurrencyProvided() {
        String sourceCurrency = "USD";
        ExchangeRateResponseDto expectedResponse = new ExchangeRateResponseDto();
        when(restTemplate.getForObject(anyString(), any())).thenReturn(expectedResponse);

        ExchangeRateResponseDto actualResponse = httpClientExchangeRateService.getExchangeRates(sourceCurrency);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getExchangeRatesReturnsNullWhenInvalidCurrencyProvided() {
        String sourceCurrency = "INVALID";
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        ExchangeRateResponseDto actualResponse = httpClientExchangeRateService.getExchangeRates(sourceCurrency);

        assertEquals(null, actualResponse);
    }
}