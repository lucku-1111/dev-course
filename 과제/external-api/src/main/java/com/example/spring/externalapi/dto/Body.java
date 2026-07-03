package com.example.spring.externalapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {
    private Items items;
    private int pageNo;
    private int numOfRows;
    private int totalCount;
}
