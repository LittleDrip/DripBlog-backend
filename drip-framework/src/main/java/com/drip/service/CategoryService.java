package com.drip.service;

import com.drip.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drip.utils.Result;

/**
* @author qq316
* @description 针对表【dp_category(分类表)】的数据库操作Service
* @createDate 2024-05-13 18:01:02
*/
public interface CategoryService extends IService<Category> {

    Result getCategoryList();
}
