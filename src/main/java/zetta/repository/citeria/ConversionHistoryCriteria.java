package zetta.repository.citeria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zetta.domain.dto.criteria.ConversionHistoryCriteriaDto;
import zetta.domain.entity.CurrencyConversion;

public interface ConversionHistoryCriteria {

    public Page<CurrencyConversion> findCurrencyConversion(ConversionHistoryCriteriaDto conversionHistoryCriteriaDto, Pageable pageable);

}
