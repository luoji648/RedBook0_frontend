package com.zhiyan.redbookbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "登录请求：验证码登录填 phone+code；密码登录填 phone+password")
@Data
public class LoginFormDTO {
    @Schema(description = "手机号", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
    @Schema(description = "短信验证码（验证码登录）", example = "123456")
    private String code;
    @Schema(description = "密码（密码登录）", example = "Passw0rd!")
    private String password;
}
