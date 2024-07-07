package zetta.repository.citeria.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import zetta.domain.dto.criteria.ConversionHistoryCriteriaDto;
import zetta.domain.entity.CurrencyConversion;
import zetta.domain.entity.CurrencyConversion_;
import zetta.repository.citeria.ConversionHistoryCriteria;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConversionHistoryCriteriaImpl implements ConversionHistoryCriteria {

    private static final Logger log = LogManager.getLogger(ConversionHistoryCriteriaImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Page<CurrencyConversion> findCurrencyConversion(ConversionHistoryCriteriaDto conversionHistoryCriteriaDto, Pageable pageable){
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Main query
        CriteriaQuery<CurrencyConversion> criteriaQuery = criteriaBuilder.createQuery(
                CurrencyConversion.class);
        Root<CurrencyConversion> currencyConversionRoot = criteriaQuery.from(CurrencyConversion.class);
        List<Predicate> currencyConversionPredicates = getPredicatesByCurrencyConversionSearchCriteria(
                criteriaBuilder, currencyConversionRoot, conversionHistoryCriteriaDto);
        applyWhereClause(criteriaBuilder, criteriaQuery, currencyConversionPredicates);


        // Setup pages
        // Apply Pagination to TypedQuery
        TypedQuery<CurrencyConversion> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<CurrencyConversion> currencyConversions = typedQuery.getResultList();

        // Count Query for Pagination
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<CurrencyConversion> countRoot = countQuery.from(CurrencyConversion.class);
        List<Predicate> countQueryPredicates = getPredicatesByCurrencyConversionSearchCriteria(
                criteriaBuilder, countRoot, conversionHistoryCriteriaDto);
        countQuery.select(criteriaBuilder.countDistinct(countRoot.get(CurrencyConversion_.id)));
        applyWhereClause(criteriaBuilder, countQuery, countQueryPredicates);

        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(currencyConversions, pageable, count);
    }

    private List<Predicate> getPredicatesByCurrencyConversionSearchCriteria(CriteriaBuilder criteriaBuilder, Root<CurrencyConversion> currencyConversionRoot, ConversionHistoryCriteriaDto conversionHistoryCriteriaDto) {
        List<Predicate> predicates = new ArrayList<>();



        if (!CollectionUtils.isEmpty(conversionHistoryCriteriaDto.getTransactionId())) {
            predicates.add(currencyConversionRoot.get(CurrencyConversion_.transactionId)
                    .in(conversionHistoryCriteriaDto.getTransactionId()));
            log.info("Predicate for transaction Id has been added: {}", conversionHistoryCriteriaDto.getTransactionId());
        }

        if (Objects.nonNull(conversionHistoryCriteriaDto.getTransactionDate())) {

            // Extract date part from OffsetDateTime (conversionTime)
            Expression<LocalDate> conversionTimeDate = currencyConversionRoot.get(CurrencyConversion_.conversionTime).as(OffsetDateTime.class)
                    .as(LocalDate.class);

            // Create predicates to compare dates only -> (ignore time)
            Predicate datePredicate = criteriaBuilder.equal(conversionTimeDate, conversionHistoryCriteriaDto.getTransactionDate());

            predicates.add(datePredicate);

            log.info("Predicate for transaction Date has been added: {}", conversionHistoryCriteriaDto.getTransactionDate());
        }


        return predicates;
    }

    private static <T> void applyWhereClause(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> query,
                                             List<Predicate> predicates) {
        if (predicates != null) {
            Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
            query.where(criteriaBuilder.and(predicateArray));
        }
    }



}
