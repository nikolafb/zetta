package zetta.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import zetta.domain.dto.ExchangeRateDto;
import zetta.service.CurrencyService;
import zetta.util.locale.LocaleUtil;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/test/{test}")
    public String getContactTest(@PathVariable("test") String test) {
        return LocaleUtil.getLocaleMassage(test);
    }

    @Operation(summary = "Get exchange rate between two currencies by source and target currency."
            , description = "Get exchange rate between two currencies by source and target currency. " +
            "The exchange rate is based on the latest data from the exchange rate API." )
    @GetMapping("/exchange-rate")
    public ExchangeRateDto getExchangeRate(@RequestHeader(name = "Accept-Language", defaultValue = "en") String acceptLanguage,
                                           @RequestParam String sourceCurrency, @RequestParam String targetCurrency) {
        return currencyService.getExchangeRate(sourceCurrency, targetCurrency);
    }
}
