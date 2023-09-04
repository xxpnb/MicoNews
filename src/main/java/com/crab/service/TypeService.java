package com.crab.service;

import com.crab.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crab.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
public interface TypeService extends IService<Type> {

    //查询所有类别数据
    Result findAllTypes();
}
