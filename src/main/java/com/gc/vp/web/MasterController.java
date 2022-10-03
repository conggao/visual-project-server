package com.gc.vp.web;

import com.gc.vp.entity.po.UserPo;
import com.gc.vp.exception.BusinessException;
import com.gc.vp.repository.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 注册用户 默认开启白名单
     *
     * @param user
     */
    @PostMapping("/register")
    public UserPo register(@RequestBody UserPo user) {
        UserPo bizUser = userRepository.findByUsername(user.getUsername());
        if (null != bizUser) {
            throw new BusinessException(1001, "用户已经存在");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    @GetMapping("/userList")
    public Map<String, Object> userList() {
        List<UserPo> users = userRepository.findAll();
        log.info("users: {}", users);
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
