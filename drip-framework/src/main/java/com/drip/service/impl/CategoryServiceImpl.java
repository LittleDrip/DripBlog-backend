package com.drip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drip.constants.SystemConstants;
import com.drip.domain.Article;
import com.drip.domain.Category;
import com.drip.domain.vo.CategoryVo;
import com.drip.service.ArticleService;
import com.drip.service.CategoryService;
import com.drip.mapper.CategoryMapper;
import com.drip.utils.BeanCopyUtils;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author qq316
* @description 针对表【dp_category(分类表)】的数据库操作Service实现
* @createDate 2024-05-13 18:01:02
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleService articleService;
    @Override
    public Result getCategoryList() {
//        查询文章表，获取status为已发布的category_id并且去重
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);//获取文章表
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
//        查询status为正常的分类
        List<Category> categories = listByIds(categoryIds);
         categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

//        封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return Result.ok(categoryVos);
    }
}




