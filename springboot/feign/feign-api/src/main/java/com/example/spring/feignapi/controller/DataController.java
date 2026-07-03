package com.example.spring.feignapi.controller;

import com.example.spring.feignapi.dto.DataRequest;
import com.example.spring.feignapi.dto.DataResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

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
    public DataResponse getDataById(
            @PathVariable Long id
    ) {
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

    @PutMapping("/{id}")
    public DataResponse updateData(
            @PathVariable("id") Long id, // "id"는 매개변수 값이 id로 경로 변수값과 이름이 일치해서 넣지 않아도 되지만 학습용으로 넣음
            @RequestBody DataRequest dataRequest
    ) {
        DataResponse dataResponse = dataStore.get(id);

        if (dataResponse == null) throw new RuntimeException("Data not found(update): " + id);

        dataResponse.setName(dataResponse.getName());
        dataResponse.setValue(dataResponse.getValue());
        dataStore.put(id, dataResponse);

        return dataResponse;
    }

    @DeleteMapping("/{id}")
    public String deleteData(
            @PathVariable Long id
    ) {
        DataResponse removed = dataStore.remove(id);

        if (removed == null) throw new RuntimeException("Data not found(delete): " + id);

        return "Data deleted with id:" + id;
    }
}
