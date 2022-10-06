package com.gc.vp.service;

import com.gc.vp.entity.po.UserPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author conggao
 * @since 2022-10-06
 */
public interface IUserService extends IService<UserPo> {

    UserPo findByUserName(String username);
}
