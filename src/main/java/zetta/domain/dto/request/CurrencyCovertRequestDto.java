package zetta.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class CurrencyCovertRequestDto {
    //Input: An amount in the source currency, source currency code, and target
    //currency code

    @Schema(description = "The amount in the source currency. The amount be grader than 0 and not more than 1000000", example = "100")
    @Min(value = 1, message = "{exchange.amount.min}")
    @Max(value = 1000000, message = "{exchange.amount.max}")
    private Double amount;

    @Schema(description = "The source currency code. The code should be 3 characters long", example = "USD")
    @Size(min = 3, max = 3, message = "{exchange.sourceCurrency}")
    private String sourceCurrency;

    @Schema(description = "The target currency code. The code should be 3 characters long", example = "EUR")
    @Size(min = 3, max = 3, message = "{exchange.targetCurrency}")
    private String targetCurrency;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

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
}
