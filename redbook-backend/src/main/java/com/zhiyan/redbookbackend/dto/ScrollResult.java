package com.zhiyan.redbookbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "关注动态流式分页结果（基于时间游标）")
@Data
public class ScrollResult {
    @Schema(description = "本页博客列表")
    private List<?> list;
    @Schema(description = "本页最小时间戳，作为下次请求的 lastId", example = "1698825600000")
    private Long minTime;
    @Schema(description = "与上次查询重复时的偏移量", example = "0")
    private Integer offset;
}
