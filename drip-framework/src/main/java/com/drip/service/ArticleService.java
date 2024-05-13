package com.drip.service;

import com.drip.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.drip.utils.Result;

/**
* @author qq316
* @description 针对表【dp_article(文章表)】的数据库操作Service
* @createDate 2024-05-12 20:08:43
*/
public interface ArticleService extends IService<Article> {

    Result hotArticleList();

    Result articleList(Long categoryId, Integer pageNum, Integer pageSize);
}
