package com.jobnet.common.utils.pagination;

import com.jobnet.common.utils.MongoUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.Function;

public class PaginationUtil {

    public static <T> Pageable getPageable(Integer page, Integer pageSize, List<String> sortBys) {
        return PageRequest.of(
            page - 1,
            pageSize,
            MongoUtil.getSort(sortBys)
        );
    }

    public static <T> Pageable getPageable(PaginationFilter filter) {
        return PageRequest.of(
            filter.getPage() - 1,
            filter.getPageSize(),
            MongoUtil.getSort(filter.getSortBys())
        );
    }

    public static <T> Page<T> getPage(MongoTemplate mongoTemplate, Query query, Pageable pageable, Class<T> tClass) {
        long count = mongoTemplate.count(query, tClass);
        List<T> jobSeekers = mongoTemplate.find(query.with(pageable), tClass);
        return PageableExecutionUtils.getPage(
            jobSeekers,
            pageable,
            () -> count
        );
    }

    public static <T, R> PaginationResponse<List<R>> getPaginationResponse(Page<T> page, Function<T, R> mapper) {
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int currentPage = page.getNumber() + 1;
        boolean hasNextPage = page.hasNext();
        List<R> rList = page.getContent().stream()
            .map(mapper)
            .toList();
        return PaginationResponse.<List<R>>builder()
            .totalElements(totalElements)
            .totalPages(totalPages)
            .currentPage(currentPage)
            .hasNextPage(hasNextPage)
            .data(rList)
            .build();
    }
}
