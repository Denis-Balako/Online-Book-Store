package com.balako.onlinebookstore.dto.book;

public record BookSearchParametersDto(String[] title, String[] author, String[] isbn) {
}
