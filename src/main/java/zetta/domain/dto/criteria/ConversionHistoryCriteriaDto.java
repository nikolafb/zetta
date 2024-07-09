package zetta.domain.dto.criteria;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public class ConversionHistoryCriteriaDto {

    @Schema(description = "To be filtered by the passed date. All transactions will be showed if no date is passed." +
            "The format uses the ISO-8601 date format (yyyy-MM-dd)."
            , example = "2024-07-11")
    private LocalDate transactionDate;

    @Schema(description = "To be filtered by the transactionId. All transactions will be showed if no " +
            "transaction Id is passed.", example = "123456")
    private List<String> transactionId;

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<String> getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(List<String> transactionId) {
        this.transactionId = transactionId;
    }
}
