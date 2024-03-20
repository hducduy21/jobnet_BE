package com.jobnet.common.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class MongoUtil {

    public static Sort getSort(List<String> sortBy) {
        List<Sort.Order> orders = new ArrayList<>();

        for (String param: sortBy) {
            String[] parts = param.split("-");
            if (parts.length == 2) {
                String field = parts[0].trim();
                String direction = parts[1].trim();
                Sort.Order order = "asc".equals(direction)
                    ? Sort.Order.asc(field)
                    : Sort.Order.desc(field);
                orders.add(order);
            } else {
                Sort.Order order = Sort.Order.desc(parts[0].trim());
                orders.add(order);
            }
        }
        return Sort.by(orders);
    }
}
