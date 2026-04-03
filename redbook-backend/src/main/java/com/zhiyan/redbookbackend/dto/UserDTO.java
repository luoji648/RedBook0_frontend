package com.zhiyan.redbookbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户简要信息（脱敏展示）")
@Data
public class UserDTO {
    @Schema(description = "用户ID", example = "101")
    private Long id;
    @Schema(description = "昵称", example = "探店达人")
    private String nickName;
    @Schema(description = "头像URL", example = "https://example.com/avatar/101.png")
    private String icon;
}
