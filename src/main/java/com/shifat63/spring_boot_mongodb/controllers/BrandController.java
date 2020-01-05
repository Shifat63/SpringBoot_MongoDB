package com.shifat63.spring_boot_mongodb.controllers;

import com.shifat63.spring_boot_mongodb.model.Brand;
import com.shifat63.spring_boot_mongodb.services.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

// Author: Shifat63

@Slf4j
@Controller
public class BrandController {
    private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping({"/brand", "/brand/", "/brand/index"})
    public String getBrandIndex(Model model) throws Exception{
        log.info("start: getBrandIndex method of BrandController");
        model.addAttribute("brands", brandService.findAll());
        log.info("end: getBrandIndex method of BrandController");
        return "brand/index";
    }

    @RequestMapping("/brand/view/{brandId}")
    public String viewBrand(@PathVariable("brandId") String brandId, Model model) throws Exception{
        log.info("start: viewBrand method of BrandController");
        model.addAttribute("brand", brandService.findById(brandId));
        log.info("end: viewBrand method of BrandController");
        return "brand/view";
    }

    @RequestMapping("/brand/add")
    public String addBrandGet(Model model) throws Exception{
        log.info("start: addBrandGet method of BrandController");
        model.addAttribute("brand", new Brand());
        log.info("end: addBrandGet method of BrandController");
        return "brand/add";
    }

    @RequestMapping(value = "/brand/add", method = RequestMethod.POST)
    public String addOrEditBrandPost(@Valid @ModelAttribute Brand brand, BindingResult bindingResult, Model model) throws Exception
    {
        log.info("start: addOrEditBrandPost method of BrandController");

        if(bindingResult.hasErrors()){
            log.error("error: addOrEditBrandPost method of BrandController -> Brand model validation failed");
            return "brand/add";
        }

        Brand savedBrand = brandService.saveOrUpdate(brand);
        log.info("end: addOrEditBrandPost method of BrandController");
        return "redirect:/brand/view/" + savedBrand.getId();
    }

    @RequestMapping("brand/edit/{brandId}")
    public String editBrandGet(@PathVariable("brandId") String brandId, Model model) throws Exception
    {
        log.info("start: editBrandGet method of BrandController");
        model.addAttribute("brand", brandService.findById(brandId));
        log.info("end: editBrandGet method of BrandController");
        return "brand/add";
    }

    @RequestMapping("/brand/delete/{brandId}")
    public String deleteBrand(@PathVariable("brandId") String brandId) throws Exception
    {
        log.info("start: deleteBrand method of BrandController");
        brandService.deleteById(brandId);
        log.info("end: deleteBrand method of BrandController");
        return "redirect:/brand/index";
    }
}

