package com.example.spring.externalapi.client;

import com.example.spring.externalapi.dto.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "${feign-api.url}")
public interface WeatherCilent {

    @GetMapping("/getUltraSrtNcst")
    WeatherResponse getUltraSrtNcst(
            @RequestParam String serviceKey,
            @RequestParam int numOfRows,
            @RequestParam int pageNo,
            @RequestParam String dataType,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime,
            @RequestParam int nx,
            @RequestParam int ny
            );
}
