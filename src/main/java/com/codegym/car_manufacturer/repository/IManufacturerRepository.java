package com.codegym.car_manufacturer.repository;

import com.codegym.car_manufacturer.model.dto.ManufacturerDTO;
import com.codegym.car_manufacturer.model.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Page<Manufacturer> findAll(Pageable pageable);
    @Query(nativeQuery = true, value = "select manufacturers.name, count(*) as number from cars join manufacturers on cars.manufacturer_id = manufacturers.id group by manufacturers.name order by number ")
    Iterable<ManufacturerDTO> getCountManufacturers();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "call dsp_delete_manufacturer(:id) ")
    void deleteManufacturerById(@Param("id") Long id);
}
