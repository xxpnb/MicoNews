package com.crab.service;

import com.crab.entity.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crab.entity.vo.PortalVo;
import com.crab.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline, String token);

    Result updateData(Headline headline);
}
