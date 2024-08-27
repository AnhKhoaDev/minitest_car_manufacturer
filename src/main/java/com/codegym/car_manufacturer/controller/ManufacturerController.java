package com.codegym.car_manufacturer.controller;

import com.codegym.car_manufacturer.model.entity.Manufacturer;
import com.codegym.car_manufacturer.service.IManufacturerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final IManufacturerService manufacturerService;

    public ManufacturerController(IManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("")
    public ModelAndView index(@PageableDefault(value = 2) Pageable pageable) {
        ModelAndView mav = new ModelAndView("/manufacturer/index");
        Page<Manufacturer> manufacturers = manufacturerService.findAll(pageable);
        mav.addObject("manufacturers", manufacturers);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("/manufacturer/delete");
        Manufacturer manufacturer  = manufacturerService.findCarById(id);
        modelAndView.addObject("manufacturer", manufacturer);
        return modelAndView;
    }
    @PostMapping("/delete")
    public String delete(Manufacturer manufacturer,
                         RedirectAttributes redirectAttributes) {
        manufacturerService.deleteManufacturerById(manufacturer.getId());
        redirectAttributes.addFlashAttribute("message", "Delete manufacturer successfully");
        return "redirect:/manufacturers";
    }
}
