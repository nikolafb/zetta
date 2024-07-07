package zetta.domain.dto;



import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public class ConversionHistoryDto {

    @Schema(description = "The source currency code", example = "USD")
    private String source;
    @Schema(description = "The target currency code", example = "EUR")
    private String target;
    @Schema(description = "The exchange rate used for the conversion", example = "1.2")
    private Double exchangeRate;
    @Schema(description = "The converted amount in the target currency", example = "100")
    private Double convertedAmount;
    @Schema(description = "The time the conversion was made", example = "2024-07-11T00:00:00Z")
    private OffsetDateTime conversionTime;
    @Schema(description = "A unique transaction identifier", example = "123456")
    private String transactionId;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public OffsetDateTime getConversionTime() {
        return conversionTime;
    }

    public void setConversionTime(OffsetDateTime conversionTime) {
        this.conversionTime = conversionTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public ConversionHistoryDto source(String source) {
        this.source = source;
        return this;
    }

    public ConversionHistoryDto target(String target) {
        this.target = target;
        return this;
    }

    public ConversionHistoryDto exchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public ConversionHistoryDto convertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
        return this;
    }

    public ConversionHistoryDto conversionTime(OffsetDateTime conversionTime) {
        this.conversionTime = conversionTime;
        return this;
    }

    public ConversionHistoryDto transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

}
