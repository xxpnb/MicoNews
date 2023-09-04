package com.crab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crab.entity.User;
import com.crab.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
public interface UserService extends IService<User> {
    Result login(User user);

    Result getUserInfo(String token);

    Result checkUsername(String userName);

    Result regist(User user);
}
