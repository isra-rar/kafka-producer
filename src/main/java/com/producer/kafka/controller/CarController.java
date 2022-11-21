package com.producer.kafka.controller;

import com.producer.kafka.producer.CarProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarProducer carProducer;

    public CarController(CarProducer carProducer) {
        this.carProducer = carProducer;
    }

    @PostMapping
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDTO) {
        CarDTO car = CarDTO.builder()
                .id(UUID.randomUUID().toString())
                .model(carDTO.getModel())
                .color(carDTO.getColor())
                .build();

        carProducer.send(car);

        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }
}
