package zetta.domain.mapper;

import org.springframework.stereotype.Component;
import zetta.domain.dto.request.CurrencyCovertRequestDto;
import zetta.domain.dto.response.CurrencyCovertResponseDto;
import zetta.domain.entity.CurrencyConversion;

@Component
public class CurrencyMapper {


    public CurrencyConversion mapCurrencyCovertRequestDtoToCurrencyConversion(CurrencyCovertRequestDto currencyCovertRequestDto,
                                                                              Double convertedAmount,Double exchangeRate,String transactionId) {
        return new CurrencyConversion()
                .source(currencyCovertRequestDto.getSourceCurrency())
                .target(currencyCovertRequestDto.getTargetCurrency())
                .convertedAmount(convertedAmount)
                .exchangeRate(exchangeRate)
                .transactionId(transactionId);
    }

    public CurrencyCovertResponseDto mapCurrencyConversionToCurrencyCovertResponseDto(CurrencyConversion currencyConversion) {
        return new CurrencyCovertResponseDto()
                .convertedAmount(currencyConversion.getConvertedAmount())
                .transactionId(currencyConversion.getTransactionId());
    }
}
