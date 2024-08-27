package com.codegym.car_manufacturer.service.impl;

import com.codegym.car_manufacturer.model.dto.ManufacturerDTO;
import com.codegym.car_manufacturer.model.entity.Manufacturer;
import com.codegym.car_manufacturer.repository.IManufacturerRepository;
import com.codegym.car_manufacturer.service.IManufacturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManufacturerService implements IManufacturerService {

    private final IManufacturerRepository manufacturerRepository;

    public ManufacturerService(IManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public Page<Manufacturer> findAll(Pageable pageable) {
        return manufacturerRepository.findAll(pageable);
    }

    @Override
    public Iterable<ManufacturerDTO> getCountManufacturers() {
        return manufacturerRepository.getCountManufacturers();
    }

    @Override
    public void deleteManufacturerById(Long id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public Iterable<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public void save(Manufacturer manufacturer) {

    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Manufacturer findCarById(Long id) {
        return manufacturerRepository.findById(id).get();
    }
}
