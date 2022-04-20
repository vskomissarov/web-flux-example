package ru.filit.backend.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PageUtils {

    public static Sort getSort(String sort) {
        List<String> sortProp = Arrays.asList(sort.split(","));
        Sort.Direction direction = getDirection(sortProp);
        List<Sort.Order> orders = getSortOrders(direction, sortProp);
        return Sort.by(orders);
    }

    private static List<Sort.Order> getSortOrders(Sort.Direction direction, List<String> sortProp) {
        return sortProp.stream()
                .filter(str -> !isDirection(str))
                .map(str -> new Sort.Order(direction, str))
                .collect(Collectors.toList());
    }

    private static Sort.Direction getDirection(List<String> sortProp) {
        return Sort.Direction.fromString(sortProp
                .stream()
                .filter(PageUtils::isDirection)
                .findFirst()
                .orElse("desc"));
    }

    private static boolean isDirection(String str) {
        return str.equals("asc") || str.equals("desc");
    }
}
