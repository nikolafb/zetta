package zetta.httpclient.impl;

import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import zetta.config.properties.Properties;
import zetta.domain.dto.client.ExchangeRateResponseDto;
import zetta.exception.EntityNotFoundException;
import zetta.exception.TechnicalException;
import zetta.httpclient.HttpClientExchangeRateService;
import zetta.util.locale.LocaleUtil;


@Service
public class HttpClientExchangeRateServiceImpl implements HttpClientExchangeRateService {

    private final Logger log = LogManager.getLogger(HttpClientExchangeRateServiceImpl.class);

    private final RestTemplate restTemplate;
    private final Properties.ExchangeRateApi exchangeRateApi;

    public HttpClientExchangeRateServiceImpl(RestTemplate restTemplate,
                                             Properties.ExchangeRateApi exchangeRateApi) {
        this.restTemplate = restTemplate;
        this.exchangeRateApi = exchangeRateApi;
    }

    @Override
    public ExchangeRateResponseDto getExchangeRates(String sourceCurrency) {
        final String url = exchangeRateApi.getUrl() + exchangeRateApi.getKey() + exchangeRateApi.getInfo()+sourceCurrency;
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);

        String uri = uriComponentsBuilder.toUriString();

        try {
            return restTemplate.getForObject(uri, ExchangeRateResponseDto.class);
        }catch (HttpClientErrorException e){
            // will handle only client errors
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Source currency not found: {}", sourceCurrency);
                throw new EntityNotFoundException(LocaleUtil.getLocaleMassage("exchange.source.not.found"));
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                log.error("Bad request: {}", sourceCurrency);
                throw new TechnicalException(LocaleUtil.getLocaleMassage("exchange.bad.request"));
            } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                log.error("Server error: " + e.getMessage());
                throw new TechnicalException(LocaleUtil.getLocaleMassage("exchange.server.error"));
            } else {
                log.error("Error while calling exchange rate API: {}", e.getMessage());
            }
            return null;
        }catch (Exception e){
            log.error("Error fetching exchange rate API: {}", e.getMessage());
            throw new TechnicalException(LocaleUtil.getLocaleMassage("exchange.server.error.something.went.wrong"));
        }


    }

}
