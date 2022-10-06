package com.gc.vp.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gc.vp.entity.po.UserPo;
import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.entity.vo.master.RegisterReq;
import com.gc.vp.exception.BusinessException;
import com.gc.vp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 账号相关
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class MasterController {
    @Autowired
    private IUserService userService;
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 注册用户 默认开启白名单
     *
     * @param registerReq
     */
    @PostMapping("/register")
    public TransDto<Boolean> register(@RequestBody RegisterReq registerReq) {
        UserPo bizUser = userService.getOne(new LambdaQueryWrapper<UserPo>().eq(UserPo::getUserName, registerReq.getUserName()));
        if (null != bizUser) {
            throw new BusinessException(1001, "用户已经存在");
        }
        String password = bCryptPasswordEncoder.encode(registerReq.getPassword());
        return TransDto.success(userService.save(new UserPo(registerReq.getUserName(), password, "system", "system")));
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @GetMapping("/userList")
    public Map<String, Object> userList() {
        List<UserPo> users = userService.list();
        Map<String, Object> map = new HashMap<>();
        map.put("users", users);
        return map;
    }

    /**
     * 获取用户权限
     *
     * @return
     */
    @GetMapping("/authorityList")
    public List<String> authorityList() {
        List<String> authentication = getAuthentication();
        return authentication;
    }

    /**
     * 获取用户所拥有的权限列表
     *
     * @return
     */
    public List<String> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> list = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            log.info("权限列表：{}", grantedAuthority.getAuthority());
            list.add(grantedAuthority.getAuthority());
        }
        return list;
    }

}
