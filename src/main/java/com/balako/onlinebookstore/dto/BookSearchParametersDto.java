package com.balako.onlinebookstore.dto;

public record BookSearchParametersDto(String[] title, String[] author, String[] isbn) {
}
