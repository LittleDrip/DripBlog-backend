package com.drip.controller;

import com.drip.service.CategoryService;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("getCategoryList")
    public Result getCategoryList(){
        Result result=categoryService.getCategoryList();
        return result;
    }
}
