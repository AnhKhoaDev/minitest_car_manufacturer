package com.codegym.car_manufacturer.repository;

import com.codegym.car_manufacturer.model.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarRepository extends JpaRepository<Car, Long> {
    Page<Car> findAll (Pageable pageable);

    Page<Car> findByNameContaining (String name, Pageable pageable);
}
