package zetta.httpclient;

import zetta.domain.dto.client.ExchangeRateResponseDto;

public interface HttpClientExchangeRateService {
    public ExchangeRateResponseDto getExchangeRates(String sourceCurrency);
}
