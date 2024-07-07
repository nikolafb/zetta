package zetta.httpclient;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import zetta.domain.dto.client.ExchangeRateResponseDto;


@Service
public class HttpClientExchangeRateServiceImpl {

    private final Logger log = LogManager.getLogger(HttpClientExchangeRateServiceImpl.class);

    private String apiKey = "YOUR";
    private String url = "https://v6.exchangerate-api.com/v6/"+apiKey+"/latest";

    private final RestTemplate restTemplate;

    public HttpClientExchangeRateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ExchangeRateResponseDto getExchangeRates(String sourceCurrency) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url)
                .queryParam("base", sourceCurrency);

        String uri = uriComponentsBuilder.toUriString();

        try {
            return restTemplate.getForObject(uri, ExchangeRateResponseDto.class);
        }catch (HttpClientErrorException e){
            // will handle only client errors
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("Resource not found: {}", sourceCurrency);
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                log.error("Bad request: {}", sourceCurrency);
            } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                log.error("Server error: " + e.getMessage());
            } else {
                log.error("Error while calling exchange rate API: {}", e.getMessage());
            }
            return null;
        }catch (Exception e){
            log.error("Error fetching exchange rate API: {}", e.getMessage());
            return null;
        }


    }

}
