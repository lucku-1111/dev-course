package com.example.spring.feignapi.controller;

import com.example.spring.feignapi.dto.DataRequest;
import com.example.spring.feignapi.dto.DataResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DataController {

    private Map<Long, DataResponse> dataStore = new HashMap<>();
    private Long idCounter = 1L;

    @PostConstruct
    public void initDataStore() {
        dataStore.put(idCounter++, new DataResponse(1L, "Item 1", 100));
        dataStore.put(idCounter++, new DataResponse(2L, "Item 2", 200));
        dataStore.put(idCounter++, new DataResponse(3L, "Item 3", 300));
        dataStore.put(idCounter++, new DataResponse(4L, "Item 4", 400));
        dataStore.put(idCounter++, new DataResponse(5L, "Item 5", 500));
    }

    @GetMapping("/{id}")
    public DataResponse getDataById(Long id) {
        DataResponse dataResponse = dataStore.get(id);

        if (dataResponse == null) throw new RuntimeException("Data not found: " + id);

        return dataResponse;
    }

    @PostMapping
    public DataResponse createData(
            @RequestBody DataRequest dataRequest
            ) {
        DataResponse build = DataResponse.builder()
                .id(idCounter++)
                .value(dataRequest.getValue())
                .name(dataRequest.getName())
                .build();

        dataStore.put(build.getId(), build);

        return build;
    }
}
