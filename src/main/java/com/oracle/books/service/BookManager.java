package com.oracle.books.service;

import com.oracle.books.dto.Books;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BookManager {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
    private AtomicInteger bookIdGenerator = new AtomicInteger(0);

    private ConcurrentMap<String, Books> inMemoryStore = new ConcurrentHashMap<>();

    public BookManager() {
        Books book = new Books();
        book.setId(getNextId());
        book.setName("Building Microservice With Oracle Helidon");
        book.setAuthor("baeldung");
        book.setPages(560);
        inMemoryStore.put(book.getId(), book);
    }

    private String getNextId() {
        String date = LocalDate.now().format(formatter);
        return String.format("%04d-%s", bookIdGenerator.incrementAndGet(), date);
    }

    public String add(Books book) {
        String id = getNextId();
        book.setId(id);
        inMemoryStore.put(id, book);
        return id;
    }

    public Books get(String id) {
        return inMemoryStore.get(id);
    }

    public List<Books> getAll() {
        List<Books> books = new ArrayList<>();
        books.addAll(inMemoryStore.values());
        return books;
    }
}
