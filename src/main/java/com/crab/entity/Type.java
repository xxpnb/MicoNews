package com.crab.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
@Data

public class Type implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId
    private Integer tid;


    private String tname;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;


}
