package com.codegym.car_manufacturer.service;

import com.codegym.car_manufacturer.model.dto.ManufacturerDTO;
import com.codegym.car_manufacturer.model.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IManufacturerService extends IGenerateService<Manufacturer> {
    Page<Manufacturer> findAll(Pageable pageable);
    Iterable<ManufacturerDTO> getCountManufacturers();
    void deleteManufacturerById(@Param("id") Long id);
}
