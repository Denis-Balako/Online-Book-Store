package com.balako.onlinebookstore.dto.book.request;

public record BookSearchParametersDto(String[] title, String[] author, String[] isbn) {
}
