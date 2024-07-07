package zetta.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExchangeRateDto {

    @Schema(description = "The source currency to convert from.")
    private String sourceCurrency;
    @Schema(description = "The target currency to convert to.")
    private String targetCurrency;
    @Schema(description = "The exchange rate between the source and target currency.")
    private Double exchangeRate;

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public ExchangeRateDto targetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
        return this;
    }

    public ExchangeRateDto exchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public ExchangeRateDto sourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
        return this;
    }
}
