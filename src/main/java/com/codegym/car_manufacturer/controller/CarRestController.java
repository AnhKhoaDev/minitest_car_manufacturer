package com.codegym.car_manufacturer.controller;

import com.codegym.car_manufacturer.model.entity.Car;
import com.codegym.car_manufacturer.service.ICarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cars")
public class CarRestController {
    private final ICarService carService;

    public CarRestController(ICarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = (List<Car>) carService.findAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Car> findCarById(@PathVariable Long id) {
        Optional<Car> car = carService.findById(id);
        if (!car.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Car> saveStudent(@RequestBody Car student) {
        return new ResponseEntity<>(carService.saveCar(student), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateStudent(@PathVariable Long id, @RequestBody Car student) {
        Optional<Car> car = carService.findById(id);
        if (!car.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        student.setId(car.get().getId());
        return new ResponseEntity<>(carService.saveCar(student), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteStudent(@PathVariable Long id) {
        Optional<Car> optionalStudent = carService.findById(id);
        if (!optionalStudent.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        carService.remove(id);
        return new ResponseEntity<>(optionalStudent.get(), HttpStatus.OK);
    }
}
