package com.gc.vp.config.filter;

import com.gc.vp.constant.ConstantKey;
import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.utils.JsonUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 自定义JWT登录过滤器
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 尝试身份认证(接收并解析用户凭证)
     *
     * @param req
     * @param res
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
//        try {
//            UserPo user = new ObjectMapper().readValue(req.getInputStream(), UserPo.class);
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            user.getUsername(),
//                            user.getPassword(),
//                            new ArrayList<>())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    /**
     * 认证成功(用户成功登录后，这个方法会被调用，我们在这个方法里生成token)
     *
     * @param request
     * @param response
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // builder the token
        String token = null;
        try {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            // 定义存放角色集合的对象
            List roleList = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                roleList.add(grantedAuthority.getAuthority());
            }

            // 生成token start
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            // 设置签发时间
            calendar.setTime(new Date());
            // 设置过期时间
            calendar.add(Calendar.MINUTE, 5);// 5分钟
            Date time = calendar.getTime();
            token = Jwts.builder()
                    .setSubject(auth.getName() + "-" + roleList)
                    .setIssuedAt(now)//签发时间
                    .setExpiration(time)//过期时间
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 生成token end

            // 登录成功后，返回token到header里面
            /*response.addHeader("Authorization", "Bearer " + token);*/

            // 登录成功后，返回token到body里面
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("Authorization", token);
            TransDto result = TransDto.success(resultMap);
            response.getWriter().write(JsonUtils.stringify(result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}