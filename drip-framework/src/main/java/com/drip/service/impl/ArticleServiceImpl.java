package com.drip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drip.constants.SystemConstants;
import com.drip.domain.Article;
import com.drip.domain.Category;
import com.drip.domain.vo.ArticleDetailVo;
import com.drip.domain.vo.ArticleListVo;
import com.drip.domain.vo.HotArticleVo;
import com.drip.domain.vo.PageVo;
import com.drip.mapper.CategoryMapper;
import com.drip.service.ArticleService;
import com.drip.mapper.ArticleMapper;
import com.drip.service.CategoryService;
import com.drip.utils.BeanCopyUtils;
import com.drip.utils.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryMapper categoryMapper;

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
        List<HotArticleVo> hotArticles = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return Result.ok(hotArticles);
    }

    @Override
    public Result articleList(Long categoryId, Integer pageNum, Integer pageSize) {
        //只能查询正式发布的文章
        //置顶文章显示在最前面
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
//        查询categoryName
//        通过ID来查name
        articles = articles.stream()
                .map(article -> article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
//        vo接收封装结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return Result.ok(pageVo);
    }

    @Override
    public Result getArticleDetail(Long id) {
//        根据id查询文章
        Article article = articleMapper.selectById(id);
        Category category = categoryMapper.selectById(article.getCategoryId());
        article.setCategoryName(category.getName());
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return Result.ok(articleDetailVo);
    }
}




