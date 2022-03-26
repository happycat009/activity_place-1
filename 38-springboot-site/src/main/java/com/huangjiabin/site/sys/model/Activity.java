package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 活动信息表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_activity")
@ApiModel(value="Activity对象", description="活动信息表")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动信息id")
    private Long id;

    @ApiModelProperty(value = "预定id")
    private Long reserveId;

    @ApiModelProperty(value = "活动文章标题")
    private String title;

    @ApiModelProperty(value = "活动文章内容")
    private String content;

    @ApiModelProperty(value = "活动人数")
    private Integer peopleNumber;

    @ApiModelProperty(value = "活动开始时间，必须在场地预定时间内")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间，必须在场地预约时间内")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "是否公布到大厅（0为不公布，1公布）")
    private Integer isPublish;

    @ApiModelProperty(value = "参加码（若is_publish=0，凭此码参加活动）")
    private Integer code;


}
