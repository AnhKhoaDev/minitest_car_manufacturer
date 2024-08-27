package com.codegym.car_manufacturer.service;

import com.codegym.car_manufacturer.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICarService extends IGenerateService<Car> {
    Page<Car> findAll(Pageable pageable);

    Page<Car> findByNameContaining(String name, Pageable pageable);

    Car saveCar(Car car);
}
