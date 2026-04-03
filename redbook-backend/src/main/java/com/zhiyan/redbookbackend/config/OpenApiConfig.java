package com.zhiyan.redbookbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j / SpringDoc OpenAPI 全局信息（中文）。
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hmDianPingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("黑马点评 API 文档")
                        .description("点评与探店业务 REST 接口，响应体统一为 Result（success、errorMsg、data、total）。")
                        .version("1.0.0")
                        .contact(new Contact().name("hm-dianping")));
    }
}
