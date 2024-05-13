package com.drip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drip.constants.SystemConstants;
import com.drip.domain.Article;
import com.drip.domain.vo.HotArticle;
import com.drip.service.ArticleService;
import com.drip.mapper.ArticleMapper;
import com.drip.utils.BeanCopyUtils;
import com.drip.utils.Result;
import com.drip.utils.ResultCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qq316
 * @description 针对表【dp_article(文章表)】的数据库操作Service实现
 * @createDate 2024-05-12 20:08:43
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        条件：正式文章 按照浏览量排序 最多10条(用分页实现)
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
//        List<HotArticle> hotArticles=new ArrayList<>();
//        for (Article article : articles) {
////            Bea·n拷贝到vo
//            HotArticle vo=new HotArticle();
//            BeanUtils.copyProperties(article,vo);
//            hotArticles.add(vo);
//        }
        List<HotArticle> hotArticles = BeanCopyUtils.copyBeanList(articles, HotArticle.class);
        return Result.ok(hotArticles);
    }
}




