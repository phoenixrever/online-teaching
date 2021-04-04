package com.phoenixhell.utils;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations .ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author phoenixhell
 * @create 2021/2/25 0025-下午 4:38
 */
//统一返回结果
@Data
public class CommonResult{

    private CommonResult(){}

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    public static CommonResult ok() {
        CommonResult commonResult =new CommonResult();
        commonResult.setSuccess(true);
        commonResult.setCode(StatusCode.SUCCESS);
        commonResult.setMessage("成功");
        return commonResult;
    }

    public static CommonResult error() {
        CommonResult commonResult =new CommonResult();
        commonResult.setSuccess(true);
        commonResult.setCode(StatusCode.ERROR);
        commonResult.setMessage("失败");
        return commonResult;
    }

    public CommonResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public CommonResult message(String message){
        this.setMessage(message);
        return this;
    }

    public CommonResult code(Integer code){
        this.setCode(code);
        return this;
    }


    public  CommonResult data(String key,Object value){
        this.getData().put(key,value);
        return this;
    }
    public  CommonResult data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

}
