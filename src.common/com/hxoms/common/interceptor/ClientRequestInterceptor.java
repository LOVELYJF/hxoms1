package com.hxoms.common.interceptor;

import com.hxoms.common.exceptions.UserCheckException;
import com.hxoms.common.utils.Constants;
import com.hxoms.common.utils.JWTUtil;
import com.hxoms.common.utils.UserInfo;
import com.hxoms.support.user.entity.User;
import com.hxoms.support.user.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    /**
     * 拦截所有的请求过滤处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())){
            return true;
        }
        String token = request.getHeader(Constants.TOKEN_KEY);
        if (token == null) {
            throw new UserCheckException("身份令牌为空");
        }
        User map = JWTUtil.parseToken(token);
        String userCode = map.getUserCode();
        if (map != null && StringUtils.isNotBlank(userCode)) {
            User user = userMapper.selectPasswordByUserCode(userCode);
            if (user == null) {
                throw new UserCheckException("用户不存在");
            }
            String password = user.getPassword();
            if (StringUtils.isBlank(password) || !password.equals(map.getPassword())) {
                throw new UserCheckException("密码不正确");
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setId(map.getId());
            userInfo.setUserName(map.getUserName());
            userInfo.setName(map.getUserName());
            userInfo.setPassword(map.getPassword());
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
