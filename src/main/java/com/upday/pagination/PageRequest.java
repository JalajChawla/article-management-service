package com.upday.pagination;

import com.upday.service.dto.SortOrder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author jalajchawla
 */
@Data
@Slf4j
public class PageRequest {
    private static final String NEW_LINE = "\n";
    private Integer pageNumber;
    private Integer pageSize;
    private SortOrder sortOrder;
    private String sortBy;

    public PageRequest(Integer pageNumber, Integer pageSize, SortOrder sortOrder, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortOrder = sortOrder;
        this.sortBy = sortBy;
    }

    @Override
    public String toString() {
        return "PageRequest [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", sortOrder=" + sortOrder
                + ", sortBy=" + sortBy + "]";
    }

    /**
     * This will be the case when no requestParameter is passed
     *
     * @param req
     * @return
     */
    public static boolean isEmptyRequest(PageRequest req) {
        if (null == req) {
            return true;
        }
        // case when object was created by default constructor, but no setters were
        // called
        if (Stream.of(req.pageNumber, req.pageSize, req.sortBy, req.sortOrder).allMatch(Objects::isNull)) {
            log.info("PageRequest is empty:{} as no requestParams are passed", req);
            return true;
        }
        return false;
    }

    /**
     * Performs validations on the request
     *
     * @param req
     * @param maxPageSize this should be defined based on the average payload size and should be configured in properties in RDS so that it is configurable
     * @return
     */
    public static String validatePaginationParams(PageRequest req, Integer maxPageSize) {
        if (isEmptyRequest(req)) {
            return "Empty/Null request Object";
        }
        List<String> validationErrors = new ArrayList<>();
        if (null == req.pageNumber || req.pageNumber < 0) {
            log.error("Minimum PageNumber =0. PageNumber :{} must be greater than 0", req.pageNumber);
            validationErrors.add("PageNumber must be greater than or equal to 0!");
        }

        if (null == req.pageSize || req.pageSize < 1) {
            log.error("PageSize :{} must be greater than 0", req.pageSize);
            validationErrors.add("PageSize must be greater than 0");
        }
        if(req.pageSize>maxPageSize){
            log.error("PageSize :{} must be less than {}", req.pageSize, maxPageSize);
            validationErrors.add("PageSize must be less than "+ maxPageSize);
        }

        if (null == req.sortOrder) {
            log.error("SortOrder :{} must be specified", req.sortOrder);
            validationErrors.add("SortOrder must be specified");
        }

        if (StringUtils.isBlank(req.sortBy)) {
            log.error("SortBy :{} must be specified", req.sortBy);
            validationErrors.add("SortBy must be specified");
        }

        return getConsolidatedError(validationErrors);
    }

    /**
     * Consolidates all the errors
     * @param validationErrors
     * @return
     */
    private static String getConsolidatedError(List<String> validationErrors) {
        if (CollectionUtils.isEmpty(validationErrors)) {
            return null;
        }
        return StringUtils.join(validationErrors, NEW_LINE);
    }

    /**
     * Gets the pagination query for mongo
     *
     * @param req
     * @return
     */
    public static Query getPaginationQueryForMongo(PageRequest req) {
        final int limit = req.pageSize;
        final int offset = req.pageNumber * req.pageSize;
        Query query = new Query();
        query.skip(offset).limit(limit);
        log.info("The pagination query is limit:{}, offset:{}, sort:{}", query.getLimit(), query.getSkip(),
                query.getSortObject());
        return query;
    }

}
