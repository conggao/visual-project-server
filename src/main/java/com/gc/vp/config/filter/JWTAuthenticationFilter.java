package com.gc.vp.config.filter;

import com.gc.vp.constant.ConstantKey;
import com.gc.vp.exception.BusinessException;
import com.gc.vp.service.impl.GrantedAuthorityImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 自定义JWT认证过滤器(需要认证的接口都需要认证token)
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasLength(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * 1. 签发时间 < 当前时间 < (签发时间+((token过期时间-token签发时间)/2)) 不刷新token
     * 2. (签发时间+((token过期时间-token签发时间)/2)) < 当前时间 < token过期时间 刷新token并返回给前端
     * 3. token过期时间 < 当前时间 跳转登录，重新登录获取token
     *
     * @param request  http请求
     * @param response http响应
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            if (!StringUtils.hasLength(token)) {
                throw new BusinessException(1002, "Token不能为空!");
            }
            // 解析token
            String user = null;

            Claims claims = Jwts.parser().setSigningKey(ConstantKey.SIGNING_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody();
            // token签发时间
            long issuedAt = claims.getIssuedAt().getTime();
            // 当前时间
            long currentTimeMillis = System.currentTimeMillis();
            // token过期时间
            long expirationTime = claims.getExpiration().getTime();

            // 验证token时间有效性
            if ((issuedAt + ((expirationTime - issuedAt) / 2)) < currentTimeMillis && currentTimeMillis < expirationTime) {
                //region 重新生成token
                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                // 设置签发时间
                calendar.setTime(new Date());
                // 设置过期时间
                calendar.add(Calendar.YEAR, 5);// 5分钟
                Date time = calendar.getTime();
                String refreshToken = Jwts.builder()
                        .setSubject(claims.getSubject())
                        .setIssuedAt(now)//签发时间
                        .setExpiration(time)//过期时间
                        .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                        .compact();
                //endregion 重新生成token end

                // 主动刷新token，并返回给前端
                response.addHeader("refreshToken", refreshToken);
            }
            user = claims.getSubject();
            log.info("登录用户{}", user);
            if (user != null) {
                String[] split = user.split("-")[1].split(",");
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    authorities.add(new GrantedAuthorityImpl(split[i]));
                }
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        } catch (ExpiredJwtException e) {
            // 异常捕获、发送到ExpiredJwtException
            request.setAttribute("expiredJwtException", e);
            // 将异常分发到ExpiredJwtException控制器
            request.getRequestDispatcher("/expiredJwtException").forward(request, response);
        } catch (UnsupportedJwtException e) {
            // 异常捕获、发送到UnsupportedJwtException
            request.setAttribute("unsupportedJwtException", e);
            // 将异常分发到UnsupportedJwtException控制器
            request.getRequestDispatcher("/unsupportedJwtException").forward(request, response);
        } catch (MalformedJwtException e) {
            // 异常捕获、发送到MalformedJwtException
            request.setAttribute("malformedJwtException", e);
            // 将异常分发到MalformedJwtException控制器
            request.getRequestDispatcher("/malformedJwtException").forward(request, response);
        } catch (SignatureException e) {
            // 异常捕获、发送到SignatureException
            request.setAttribute("signatureException", e);
            // 将异常分发到SignatureException控制器
            request.getRequestDispatcher("/signatureException").forward(request, response);
        } catch (IllegalArgumentException e) {
            // 异常捕获、发送到IllegalArgumentException
            request.setAttribute("illegalArgumentException", e);
            // 将异常分发到IllegalArgumentException控制器
            request.getRequestDispatcher("/illegalArgumentException").forward(request, response);
        }
        return null;
    }

}
