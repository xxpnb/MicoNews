package com.crab.entity.vo;

import lombok.Data;

/**
 * 分页返回类
 * description:
 */

@Data
public class PortalVo {

    private String keyWords ;
    private int type = 0;

    private int pageNum = 1;

    private int pageSize = 10;
}
