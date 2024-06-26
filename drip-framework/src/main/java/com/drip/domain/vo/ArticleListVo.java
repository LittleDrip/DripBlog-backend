package com.drip.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
    private Long id;

    private String title;

    private String summary;

    private String categoryName;
//缩略图
    private String thumbnail;

    private Long viewCount;
    private Date createTime;
}
