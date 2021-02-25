package com.phoenixhell.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author phoenixhell
 * @create 2021/2/25 0025-下午 4:38
 */
//统一返回结果
@Data
@Accessors(chain = true)
public class CommonResult {
    private CommonResult() {
    }

    private static CommonResult commonResult = new CommonResult();

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    public static CommonResult ok() {
        commonResult.setSuccess(true);
        commonResult.setCode(StatusCode.SUCCESS);
        commonResult.setMessage("成功");
        return commonResult;
    }

    public static CommonResult error() {
        commonResult.setSuccess(false);
        commonResult.setCode(StatusCode.ERROR);
        commonResult.setMessage("失败");
        return commonResult;
    }

    public static CommonResult data(String key,Object value){
        commonResult.getData().put(key,value);
        return commonResult;
    }
    public static CommonResult data(Map<String,Object> map){
        commonResult.setData(map);
        return commonResult;
    }

}
