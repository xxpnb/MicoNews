package com.crab.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crab.entity.Headline;
import com.crab.entity.vo.PortalVo;
import com.crab.mapper.HeadlineMapper;
import com.crab.service.HeadlineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crab.utils.JwtHelper;
import com.crab.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline> implements HeadlineService {

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper ;

    /**
     * 分页查询
     * 分页数据拼接result
     * 自定义mapper方法并且分页
     * 返回结果是list<Map>
     */

    @Override
    public Result findNewsPage(PortalVo portalVo) {
        //1.条件拼接 需要非空判断
//        LambdaQueryWrapper<Headline> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.like(!StringUtils.isEmpty(portalVo.getKeyWords()),Headline::getTitle,portalVo.getKeyWords())
//                .eq(portalVo.getType()!= null,Headline::getType,portalVo.getType());

        //2.分页参数
        IPage<Headline> page = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());

        //3.分页查询
        //查询的结果 "pastHours":"3"   // 发布时间已过小时数 我们查询返回一个map
        //自定义方法
        headlineMapper.selectPageMap(page, portalVo);

        //4.结果封装
        //分页数据封装
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageData", page.getRecords());
        pageInfo.put("pageNum", page.getCurrent());
        pageInfo.put("pageSize", page.getSize());
        pageInfo.put("totalPage", page.getPages());
        pageInfo.put("totalSize", page.getTotal());

        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageInfo", pageInfo);
        // 响应JSON
        return Result.ok(pageInfoMap);
    }

    /**
     * 详情数据查询
     * "headline":{
     * "hid":"1",                     // 新闻id
     * "title":"马斯克宣布 ... ...",   // 新闻标题
     * "article":"... ..."            // 新闻正文
     * "type":"1",                    // 新闻所属类别编号
     * "typeName":"科技",             // 新闻所属类别
     * "pageViews":"40",              // 新闻浏览量
     * "pastHours":"3" ,              // 发布时间已过小时数
     * "publisher":"1" ,              // 发布用户ID
     * "author":"张三"                 // 新闻作者
     * }
     * <p>
     * 是多表查询 , 方法需要自定义Mybatis支持单表
     * 需要更新浏览量+1【乐观锁】
     * 方法返回Map
     *
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {

        Map data =  headlineMapper.selectDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline",data);

        //修改阅读量 + 1
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        headline.setVersion((Integer) data.get("version"));
        //阅读量+1
        headline.setPageViews((Integer) data.get("pageViews") +1);
        headlineMapper.updateById(headline);

        return Result.ok(headlineMap);
    }

    @Override
    public Result publish(Headline headline, String token) {
        //token查询用户id
        int userId = jwtHelper.getUserId(token).intValue();
        //数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);

        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);

        return Result.ok(null);

    }
    /**
     * 修改头条数据
     *
     *   1.hid查询数据的最新version
     *   2.修改数据的修改时间为当前节点
     *
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version); //乐观锁
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}
