package com.crab.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.crab.entity.User;
import com.crab.mapper.UserMapper;
import com.crab.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crab.utils.JwtHelper;
import com.crab.utils.MD5Util;
import com.crab.utils.Result;
import com.crab.utils.ResultCodeEnum;
import net.bytebuddy.asm.Advice;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 登录业务
     * 1.根据账号，查询用户对象
     * 2.如果为空，查询失败 501
     * 3.对比密码 密码错误 505
     * 4.成功 根据id返回一个token， token-->result 200；
     */
    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(LambdaQueryWrapper);

        if (loginUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //对比密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            //加密密码等于loginUser拿到的pwd
            //登录成功
            System.out.println(user.getUid());
            //Interger null;
            System.out.println(loginUser.getUid());

            //String token = jwtHelper.createToken(Long.valueOf(user.getUid()));
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }

        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 1.校验token有效性
     * 2.根据token解析出userId
     * 3.根据Id查询数据
     * 4.去掉密码，返回Result
     */

    @Override
    public Result getUserInfo(String token) {

        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }else {
            int userId = jwtHelper.getUserId(token).intValue();
            User user = userMapper.selectById(userId);
            user.setUserPwd("");
            Map data = new HashMap();
            data.put("loginUser" , user);

            return Result.ok(data);
        }


    }
    /**
     * 检查账号是否可用
     * 1.根据账号进行count查询
     * 2.count == 0；可用
     * 3.count ！= 0；不可用
     *
     */
    @Override
    public Result checkUsername(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,userName);
        Long count = userMapper.selectCount(queryWrapper).longValue();
        if (count == 0){
            return Result.ok(null);
        }else
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
    }

    /**
     *  实现步骤:
     * *   1. 检查可用性
     * *   2. 密码加密
     * 3.保持账号密码
     * *   3. 判断结果,成 返回200 失败 505
     */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        long count = userMapper.selectCount(queryWrapper).longValue();

        if (count > 0){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }

        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        System.out.println("注册成功");
        return Result.ok(null);
    }
}
