package com.drip.controller;

import com.drip.service.ArticleService;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    //    @GetMapping("list")
//    public String test(){
//        return articleService.list().toString();
//    }
    @GetMapping("hotArticleList")
    public Result hotArticleList() {
//        查询热门文章封装
        Result result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("articleList")
    public Result articleList(Long categoryId,Integer pageNum,Integer pageSize){
        Result result=articleService.articleList(categoryId,pageNum,pageSize);
        return result;
    }
    @GetMapping("/{id}")
    public Result getArticleDetail(@PathVariable Long id){
        return articleService.getArticleDetail(id);
    }


}
