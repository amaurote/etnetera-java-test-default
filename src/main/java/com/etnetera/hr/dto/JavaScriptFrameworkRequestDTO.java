package com.etnetera.hr.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class JavaScriptFrameworkRequestDTO {

    @NotBlank(message = "Invalid name")
    private String name;

    @NotBlank(message = "Invalid version")
    private String version;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date depricationDate;

    @Min(value = 1, message = "Hype out of range")
    @Max(value = 5, message = "Hype out of range")
    private Integer hypeLevel;

}
