package com.example.demo.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/names")
public class NamesController {
    private final List<String> names = new ArrayList<>();
    private final Counter getCounter;
    private final Counter postCounter;

    public NamesController(MeterRegistry registry) {
        this.getCounter = Counter.builder("names_get_requests_total")
                .description("Количество GET-запросов к /names")
                .register(registry);

        this.postCounter = Counter.builder("names_post_requests_total")
                .description("Количество POST-запросов к /names")
                .register(registry);
    }

    @GetMapping
    public List<String> getNames() {
        getCounter.increment();
        return names;
    }

    @PostMapping
    public void addName(@RequestBody String name) {
        postCounter.increment();
        names.add(name);
    }
}