package com.codegym.car_manufacturer.service.impl;

import com.codegym.car_manufacturer.model.entity.Car;
import com.codegym.car_manufacturer.repository.ICarRepository;
import com.codegym.car_manufacturer.service.ICarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService implements ICarService {

    private final ICarRepository carRepository;

    public CarService(ICarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public Page<Car> findByNameContaining(String name, Pageable pageable) {
        return carRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Iterable<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car findCarById(Long id) {
        return carRepository.findById(id).get();
    }
}
