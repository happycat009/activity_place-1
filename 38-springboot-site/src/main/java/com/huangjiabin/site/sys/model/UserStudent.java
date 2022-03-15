package com.huangjiabin.site.sys.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户关联学生信息表
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_student")
@ApiModel(value="UserStudent对象", description="用户关联学生信息表")
public class UserStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "学生信息id")
    private Long studentId;

    @ApiModelProperty(value = "教师id（用户的title是teaccher的用户，这里指辅导员）")
    private Long teacherId;


}
