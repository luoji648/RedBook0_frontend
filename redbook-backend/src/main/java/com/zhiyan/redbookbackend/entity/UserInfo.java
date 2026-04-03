package com.zhiyan.redbookbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "用户扩展资料")
@TableName("tb_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，用户id
     */
    @Schema(description = "用户ID", example = "101")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 城市名称
     */
    @Schema(description = "城市", example = "上海")
    private String city;

    /**
     * 个人介绍，不要超过128个字符
     */
    @Schema(description = "个人简介", example = "爱吃火锅")
    private String introduce;

    /**
     * 粉丝数量
     */
    @Schema(description = "粉丝数", example = "120")
    private Integer fans;

    /**
     * 关注的人的数量
     */
    @Schema(description = "关注数", example = "58")
    private Integer followee;

    /**
     * 性别，0：男，1：女
     */
    @Schema(description = "性别：false 男 true 女（与库表约定一致）", example = "false")
    private Boolean gender;

    /**
     * 生日
     */
    @Schema(description = "生日", example = "1995-05-01")
    private LocalDate birthday;

    /**
     * 积分
     */
    @Schema(description = "积分", example = "500")
    private Integer credits;

    /**
     * 会员级别，0~9级,0代表未开通会员
     */
    @Schema(description = "会员等级相关标记（与库表一致）", example = "false")
    private Boolean level;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2024-01-01T10:00:00")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2024-06-01T12:00:00")
    private LocalDateTime updateTime;


}
