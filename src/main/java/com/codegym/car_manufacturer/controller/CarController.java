package com.codegym.car_manufacturer.controller;

import com.codegym.car_manufacturer.model.dto.CarDTO;
import com.codegym.car_manufacturer.model.dto.ManufacturerDTO;
import com.codegym.car_manufacturer.model.entity.Car;
import com.codegym.car_manufacturer.model.entity.Manufacturer;
import com.codegym.car_manufacturer.service.ICarService;
import com.codegym.car_manufacturer.service.IManufacturerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final ICarService carService;
    private final IManufacturerService manufacturerService;

    public CarController(ICarService studentService, IManufacturerService classService) {
        this.carService = studentService;
        this.manufacturerService = classService;
    }

    @ModelAttribute("manufacturers")
    private Iterable<Manufacturer> Manufacturer() {
        return manufacturerService.findAll();
    }

    @GetMapping("")
    public ModelAndView index(@PageableDefault(value = 2) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/car/index");
        Page<Car> cars = carService.findAll(pageable);
        Iterable<ManufacturerDTO> manufacturerDTOS = manufacturerService.getCountManufacturers();
        modelAndView.addObject("cars", cars);
        modelAndView.addObject("manufacturerDTOS",manufacturerDTOS);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("search") Optional<String> search,
                               @PageableDefault(value = 2) Pageable pageable) {
        Page<Car> cars;
        if (search.isPresent()) {
            cars = carService.findByNameContaining(search.get(), pageable);
        }else {
            cars = carService.findAll(pageable);
        }
        Iterable<ManufacturerDTO> manufacturerDTOS = manufacturerService.getCountManufacturers();
        ModelAndView modelAndView = new ModelAndView("/car/index");
        modelAndView.addObject("cars",cars);
        modelAndView.addObject("search",search.orElse(""));
        modelAndView.addObject("manufacturerDTOS",manufacturerDTOS);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/car/create");
        modelAndView.addObject("car", new Car());
        return modelAndView;
    }

    @Value("${file-upload}")
    private String upload;
    @PostMapping("/create")
    public String create(@ModelAttribute("car") CarDTO carDTO,
                         RedirectAttributes redirectAttributes) {
        MultipartFile file = carDTO.getImg();

        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        Car car = new Car();
        car.setCode(carDTO.getCode());
        car.setName(carDTO.getName());
        car.setProducer(carDTO.getProducer());
        car.setPrice(carDTO.getPrice());
        car.setDescription(carDTO.getDescription());
        car.setManufacturer(carDTO.getManufacturer());
        car.setImg(fileName);
        carService.save(car);
        redirectAttributes.addFlashAttribute("message", "Create new car successfully");
        return "redirect:/cars";
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable long id) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/car/update");
            modelAndView.addObject("car", car.get());
            return modelAndView;
        }else {
            return new ModelAndView("redirect:/error_404");
        }
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("car") CarDTO carDTO,
                         RedirectAttributes redirectAttributes) {
        Car car = carService.findCarById(carDTO.getId());
        MultipartFile file = carDTO.getImg();

        String fileName = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            fileName = car.getImg();
        }
        car.setId(carDTO.getId());
        car.setCode(carDTO.getCode());
        car.setName(carDTO.getName());
        car.setProducer(carDTO.getProducer());
        car.setPrice(carDTO.getPrice());
        car.setDescription(carDTO.getDescription());
        car.setManufacturer(carDTO.getManufacturer());
        car.setImg(fileName);
        carService.save(car);
        redirectAttributes.addFlashAttribute("message", "Update car successfully");
        return "redirect:/cars";

    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable long id) {
        Optional<Car> car = carService.findById(id);
        if (car.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/car/delete");
            modelAndView.addObject("car", car.get());
            return modelAndView;
        }else
            return new ModelAndView("redirect:/error_404");
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("car") Car car,
                         RedirectAttributes redirectAttributes) {
        carService.remove(car.getId());
        redirectAttributes.addFlashAttribute("message", "Delete car successfully");
        return "redirect:/cars";
    }
}
