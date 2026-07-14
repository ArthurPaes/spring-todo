package com.sicredi.todo.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    // THE CONSTRUCTOR.
    // The only way to actually build a PagedResponse. You hand it the 6 raw values
    // and it stores them into the object's fields. Like filling in a form: "here's
    // the content, here's the page number, here's the total..." -> out comes a
    // finished object. Every `new PagedResponse<>(...)` runs THIS.
    public PagedResponse(List<T> content, int page, int size, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;

    }

    // THE STATIC FACTORY ("from").
    // Spring hands us a `Page` that already holds the content, page number, totals, etc.
    // Without this method, EVERY caller would have to dig those 6 values out of the Page
    // by hand and call the constructor itself -> the same boring code copy-pasted around.
    // This does that digging ONCE: you just write `PagedResponse.from(page)` and it pulls
    // the 6 values out and calls the constructor for you. One line instead of six.
    //
    // "static" = called on the CLASS, not an object: `PagedResponse.from(...)`. Fitting,
    // because its job is to CREATE the object -- there's no object to call it on yet.
    // (A factory that builds cars isn't itself a car.)
    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(), // current page index
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast());
    }

    // Getters required for Jackson serialization.
    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}
