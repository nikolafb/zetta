package zetta.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity(name = "currency_conversion")
public class CurrencyConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;

    private String target;

    @Column(name = "exchange_rate", nullable = false)
    private Double exchangeRate;

    @Column(name = "converted_amount", nullable = false)
    private Double convertedAmount;

    @CreationTimestamp
    @Column(name = "conversion_time")
    private OffsetDateTime conversionTime;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public CurrencyConversion transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public CurrencyConversion exchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public CurrencyConversion conversionTime(OffsetDateTime conversionTime) {
        this.conversionTime = conversionTime;
        return this;
    }

    public CurrencyConversion target(String target) {
        this.target = target;
        return this;
    }

    public CurrencyConversion source(String source) {
        this.source = source;
        return this;
    }

    public CurrencyConversion id(Long id) {
        this.id = id;
        return this;
    }

    public CurrencyConversion convertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
        return this;
    }
}
