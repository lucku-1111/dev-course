package com.example.spring.externalapi.controller;

import com.example.spring.externalapi.dto.Item;
import com.example.spring.externalapi.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public List<String> getWeather() {
         return weatherService.getReadableWeather(60, 127);
    }
}
