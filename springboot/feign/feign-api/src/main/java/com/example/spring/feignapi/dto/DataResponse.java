package com.example.spring.feignapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse {
    private Long id;
    private String name;
    private int value;
}
