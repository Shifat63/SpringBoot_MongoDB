package com.shifat63.spring_boot_mongodb.controllers;

import com.shifat63.spring_boot_mongodb.model.Product;
import com.shifat63.spring_boot_mongodb.services.service.BrandService;
import com.shifat63.spring_boot_mongodb.services.service.ProductService;
import com.shifat63.spring_boot_mongodb.services.service.ShowroomService;
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
public class ProductController {
    private ProductService productService;
    private ShowroomService showroomService;
    private BrandService brandService;

    public ProductController(ProductService productService, ShowroomService showroomService, BrandService brandService) {
        this.productService = productService;
        this.showroomService = showroomService;
        this.brandService = brandService;
    }

    @RequestMapping({"", "/product", "/product/", "/product/index"})
    public String getProductIndex(Model model) throws Exception{
        log.info("start: getProductIndex method of ProductController");
        model.addAttribute("products", productService.findAll());
        log.info("end: getProductIndex method of ProductController");
        return "product/index";
    }

    @RequestMapping("/product/view/{productId}")
    public String viewProduct(@PathVariable("productId") String productId, Model model) throws Exception{
        log.info("start: viewProduct method of ProductController");
        model.addAttribute("product", productService.findById(productId));
        log.info("end: viewProduct method of ProductController");
        return "product/view";
    }

    @RequestMapping("/product/add")
    public String addProductGet(Model model) throws Exception{
        log.info("start: addProductGet method of ProductController");
        model.addAttribute("product", new Product());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("showrooms", showroomService.findAll());
        log.info("end: addProductGet method of ProductController");
        return "product/add";
    }

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public String addOrEditProductPost(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) throws Exception
    {
        log.info("start: addOrEditProductPost method of ProductController");

        if(bindingResult.hasErrors()){
            log.error("error: addOrEditProductPost method of ProductController -> Product model validation failed");
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("showrooms", showroomService.findAll());
            return "product/add";
        }

        Product savedProduct = productService.saveOrUpdate(product);
        log.info("end: addOrEditProductPost method of ProductController");
        return "redirect:/product/view/" + savedProduct.getId();
    }

    @RequestMapping("product/edit/{productId}")
    public String editProductGet(@PathVariable("productId") String productId, Model model) throws Exception
    {
        log.info("start: editProductGet method of ProductController");
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("showrooms", showroomService.findAll());
        log.info("end: editProductGet method of ProductController");
        return "product/add";
    }

    @RequestMapping("/product/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") String productId) throws Exception
    {
        log.info("start: deleteProduct method of ProductController");
        productService.deleteById(productId);
        log.info("end: deleteProduct method of ProductController");
        return "redirect:/product/index";
    }
}
