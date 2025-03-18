package com.example.firstproject.dto;


import com.example.firstproject.entity.Coffee;

public class CoffeeDto {
    private Long id;
    private String name;
    private int price;

    public Coffee toEntity() {

        return new Coffee(id, name,price);   //폼 데이터를 담은 DTO 객체를 엔티티로 반환함
    }
}
