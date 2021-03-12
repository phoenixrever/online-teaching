package com.phoenixhell.serviceEdu.entity.vo;

import com.phoenixhell.serviceEdu.entity.EduCourse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author phoenixhell
 * @since 2021/3/12 0012-下午 1:47
 */
@Data
@NoArgsConstructor
@Component
public class Course extends EduCourse {
    @ApiModelProperty(value = "创建时间")
    private String description;
}
