package zetta.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class CurrencyCovertResponseDto {
    //Output: The converted amount in the target currency and a unique transaction
    //identifier.

    @Schema(description = "The converted amount in the target currency", example = "100")
    private Double convertedAmount;

    @Schema(description = "A unique transaction identifier", example = "123456")
    private String transactionId;

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public CurrencyCovertResponseDto convertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
        return this;
    }

    public CurrencyCovertResponseDto transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }
}
