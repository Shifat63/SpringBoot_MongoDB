package com.shifat63.spring_boot_mongodb.controllers;

import com.shifat63.spring_boot_mongodb.model.Showroom;
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
public class ShowroomController {
    private ShowroomService showroomService;

    public ShowroomController(ShowroomService showroomService) {
        this.showroomService = showroomService;
    }

    @RequestMapping({"/showroom", "/showroom/", "/showroom/index"})
    public String getShowroomIndex(Model model) throws Exception{
        log.info("start: getShowroomIndex method of ShowroomController");
        model.addAttribute("showrooms", showroomService.findAll());
        log.info("end: getShowroomIndex method of ShowroomController");
        return "showroom/index";
    }

    @RequestMapping("/showroom/view/{showroomId}")
    public String viewShowroom(@PathVariable("showroomId") String showroomId, Model model) throws Exception{
        log.info("start: viewShowroom method of ShowroomController");
        model.addAttribute("showroom", showroomService.findById(showroomId));
        log.info("end: viewShowroom method of ShowroomController");
        return "showroom/view";
    }

    @RequestMapping("/showroom/add")
    public String addShowroomGet(Model model) throws Exception{
        log.info("start: addShowroomGet method of ShowroomController");
        model.addAttribute("showroom", new Showroom());
        log.info("end: addShowroomGet method of ShowroomController");
        return "showroom/add";
    }

    @RequestMapping(value = "/showroom/add", method = RequestMethod.POST)
    public String addOrEditShowroomPost(@Valid @ModelAttribute Showroom showroom, BindingResult bindingResult, Model model) throws Exception
    {
        log.info("start: addOrEditShowroomPost method of ShowroomController");

        if(bindingResult.hasErrors()){
            log.error("error: addOrEditShowroomPost method of ShowroomController -> Showroom model validation failed");
            return "showroom/add";
        }

        Showroom savedShowroom = showroomService.saveOrUpdate(showroom);
        log.info("end: addOrEditShowroomPost method of ShowroomController");
        return "redirect:/showroom/view/" + savedShowroom.getId();
    }

    @RequestMapping("showroom/edit/{showroomId}")
    public String editShowroomGet(@PathVariable("showroomId") String showroomId, Model model) throws Exception
    {
        log.info("start: editShowroomGet method of ShowroomController");
        model.addAttribute("showroom", showroomService.findById(showroomId));
        log.info("end: editShowroomGet method of ShowroomController");
        return "showroom/add";
    }

    @RequestMapping("/showroom/delete/{showroomId}")
    public String deleteShowroom(@PathVariable("showroomId") String showroomId) throws Exception
    {
        log.info("start: deleteShowroom method of ShowroomController");
        showroomService.deleteById(showroomId);
        log.info("end: deleteShowroom method of ShowroomController");
        return "redirect:/showroom/index";
    }
}

