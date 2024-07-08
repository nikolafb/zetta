package zetta.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import zetta.domain.dto.ConversionHistoryDto;
import zetta.domain.dto.criteria.ConversionHistoryCriteriaDto;
import zetta.domain.dto.request.CurrencyCovertRequestDto;
import zetta.domain.dto.response.CurrencyCovertResponseDto;
import zetta.service.CurrencyService;
import zetta.util.locale.LocaleUtil;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "Get exchange rate between two currencies by source and target currency."
            , description = "Get exchange rate between two currencies by source and target currency. " +
            "The exchange rate is based on the latest data from the exchange rate API." )
    @GetMapping("/exchange-rate")
    public Double getExchangeRate(@RequestHeader(name = "Accept-Language", defaultValue = "en") String acceptLanguage,
                                           @RequestParam String sourceCurrency, @RequestParam String targetCurrency) {
        return currencyService.getExchangeRate(sourceCurrency, targetCurrency);
    }

    @Operation(summary = "Convert currency from source to target currency."
            , description = "Convert currency from source to target currency. " +
            "The conversion is based on the latest data from the exchange rate API. " +
            "The conversion is saved in the DB. " )
    @PostMapping("/converter")
    public CurrencyCovertResponseDto convertCurrency(@RequestHeader(name = "Accept-Language", defaultValue = "en") String acceptLanguage,
                                                     @RequestBody @Valid CurrencyCovertRequestDto currencyCovertRequestDto) {
        return currencyService.convertCurrency(currencyCovertRequestDto);
    }

    @Operation(
            summary = "Retrieve Conversion History",
            description = "Endpoint to fetch conversion records with detailed transaction information. " +
                    "Filters can be applied by transaction date and/or transaction ID. " +
                    "Multiple transaction IDs can be specified for filtering. " +
                    "If no parameters are provided, all conversions are returned. " +
                    "The default page size is set to Integer.MAX_VALUE; " +
                    "to adjust page size, include the 'pageSize' parameter in the request.")
    @PostMapping("/history")
    public Page<ConversionHistoryDto> retrieveConversionHistory(@RequestHeader(name = "Accept-Language", defaultValue = "en") String acceptLanguage,
                                                                @PageableDefault(size = Integer.MAX_VALUE) @ParameterObject Pageable pageable,
                                                                @RequestBody ConversionHistoryCriteriaDto conversionHistoryCriteriaDto){
        return currencyService.retrieveConversionHistory(conversionHistoryCriteriaDto, pageable);
    }

}
