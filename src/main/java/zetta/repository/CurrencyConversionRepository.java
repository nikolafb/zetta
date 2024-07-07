package zetta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zetta.domain.entity.CurrencyConversion;
import zetta.repository.citeria.ConversionHistoryCriteria;

@Repository
public interface CurrencyConversionRepository extends JpaRepository<CurrencyConversion, Long>, ConversionHistoryCriteria {
}
