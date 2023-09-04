package com.crab.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.crab.entity.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crab.entity.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
public interface HeadlineMapper extends BaseMapper<Headline> {
    //自定义分页查询方法
    IPage<Map> selectPageMap(IPage<Headline> page,
                             @Param("portalVo") PortalVo portalVo);

    Map selectDetailMap(Integer hid);
}
