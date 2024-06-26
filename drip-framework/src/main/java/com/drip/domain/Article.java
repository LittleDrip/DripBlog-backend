package com.drip.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @TableName dp_article
 */
@TableName(value ="dp_article")
@Data
@Accessors(chain = true)
public class Article implements Serializable {
    @TableId
    private Long id;

    private String title;

    private String content;

    private String summary;

    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

    private String thumbnail;

    private String isTop;

    private String status;

    private Long viewCount;

    private String isComment;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;
}