package com.example.demo;

public class HelloDto {

    private final Long id;
    private final String name;

    public HelloDto(Hello hello) {
        this.id = hello.getId();
        this.name = hello.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
