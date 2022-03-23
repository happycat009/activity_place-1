package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_activityb")
@ApiModel(value="Activityb对象", description="")
public class Activityb implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.ASSIGN_ID)
    private Long activityId;

    private Long userId;

    private String activityTitle;

    private String activityLable;

    private String activityType;

    private String activityLocale;

    private String activityContent;

    private String activityImg;

    private LocalDateTime createtime;

    private LocalDateTime activityStarttime;

    private LocalDateTime activityEndtime;

    private LocalDateTime updatetime;

    private Integer activityFavourNum;

    private Integer activityNum;

    private Integer activityReadNum;

    private Integer activityState;

    private Integer activityIstop;

    private Integer ispublic;

    private String activityImpose;

    private Long admin;

    private Long reserveId;


}
