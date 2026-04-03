package com.zhiyan.redbookbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "统一响应封装：success 表示是否成功；失败时 errorMsg 有值；data 为各接口业务数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Schema(description = "是否成功", example = "true")
    private Boolean success;
    @Schema(description = "失败时的错误提示", example = "手机号格式错误")
    private String errorMsg;
    @Schema(description = "成功时的业务数据，结构随接口变化")
    private Object data;
    @Schema(description = "列表/分页场景下的总条数（部分接口使用）", example = "100")
    private Long total;

    public static Result ok(){
        return new Result(true, null, null, null);
    }
    public static Result ok(Object data){
        return new Result(true, null, data, null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(true, null, data, total);
    }
    public static Result fail(String errorMsg){
        return new Result(false, errorMsg, null, null);
    }
}
