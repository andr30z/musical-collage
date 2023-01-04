package com.musical.collage.musicalcollage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musical.collage.musicalcollage.service.CollageService;

@RestController
@RequestMapping("/api/v1/collage")
public class CollageController {
    private final CollageService collageService;

    public CollageController(CollageService collageService) {
        this.collageService = collageService;
    }
    
    @GetMapping
    public Object generateCollage(){
         return collageService.generateCollage();
    }

}
