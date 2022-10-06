package com.gc.vp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gc.vp.entity.po.UserPo;
import com.gc.vp.mapper.UserMapper;
import com.gc.vp.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author conggao
 * @since 2022-10-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo> implements IUserService {

    @Override
    public UserPo findByUserName(String userName) {
        UserPo user = getOne(new LambdaQueryWrapper<UserPo>().eq(UserPo::getUserName, userName));
        return user;
    }
}
