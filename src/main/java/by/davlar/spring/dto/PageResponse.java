package by.davlar.spring.dto;

import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    @org.jetbrains.annotations.NotNull
    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
        return new PageResponse<>(page.getContent(), metadata);
    }

    @Value
    private static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}
