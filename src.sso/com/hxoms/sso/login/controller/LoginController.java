package com.hxoms.sso.login.controller;

import com.hxoms.common.CustomMessageException;
import com.hxoms.common.exceptions.UserCheckException;
import com.hxoms.common.listener.TokenExpireListener;
import com.hxoms.common.utils.*;
import com.hxoms.support.module.entity.Module;
import com.hxoms.support.module.service.ModuleService;
import com.hxoms.support.parameter.service.ParameterService;
import com.hxoms.support.user.entity.User;
import com.hxoms.support.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

@RestController
@RequestMapping(value = "/loginManage")
public class LoginController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private ModuleService moduleService;

    /**
     * @return
     * @Description: 登录功能
     */
    @RequestMapping(value = "/login")
    public Result loginCheck(User loginUser) throws Exception {
        Map tokenCacheMap = TokenExpireListener.getTokenCacheMap();
        String userCode= loginUser.getUserCode();
        String passWord = loginUser.getPassword();
        if (loginUser == null || userCode == null || passWord == null) {
            throw new CustomMessageException("账号密码不能为空");
        }
        User user = userMapper.selectPasswordByUserCode(userCode);
        if (user == null) {
            throw new CustomMessageException("用户名不存在");
        }
        String password1 = user.getPassword();
        if (password1 == null) {
            throw new CustomMessageException("密码不存在");
        }
        if (!UUIDGenerator.MD5.GetMD5Code(passWord).equals(password1)) {
            throw new CustomMessageException("密码不正确");
        }
//        String mins = parameterService.selectPValueByCode(Constants.EXPIRE_TIMES);//前端页面添加定时器
        String token = JWTUtil.createToken(user);//使用工具类创建token
//        PassTask.setPassTaskForCacheMap(token, mins);//放入线程安全map集合
        //获取当前用户下的模块权限
        Map<String, Object> map = new HashMap<>();
        //List<Module> grantModules = moduleService.selectUserGrantModules(user.getId());
        map.put("name", user.getUserName());
        map.put("userId", user.getId());
        //map.put("modules", grantModules);
        return Result.success().setData(map).setToken(token);
    }

    /**
     * @return
     * @Description: 登出功能
     */
    @RequestMapping("/loginOut")
    public Result loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader(Constants.TOKEN_KEY);
        Map tokenCacheMap = TokenExpireListener.getTokenCacheMap();
        Timer timer = (Timer) tokenCacheMap.get(token);
        if (timer != null) {
            timer.cancel();//删除定时器
        }
        tokenCacheMap.remove(token);//移除token
        return Result.success();
    }

    /**
     * 校验token，根据参数是否刷新
     */
    @RequestMapping("/checkToken")
    public Result checkToken() throws Exception {
        String token = DomainObjectUtil.getRequest().getHeader(Constants.TOKEN_KEY);
        User user = JWTUtil.parseToken(token);
        User user1 = userMapper.selectPasswordByUserCode(user.getUserCode());
        boolean flag;
        if (user1 == null) {
            throw new UserCheckException("用户不存在");
        } else {
            flag = JWTUtil.checkToken(token, user1);
        }
        if (!flag) {
            throw new UserCheckException("用户信息不正确");
        }
        return Result.success();
    }

    /**
     * 获取模块
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectRouterList")
    public Result selectRouterList() {
        List<Module> routerList = moduleService.selectRouterList();
        return Result.success(routerList);
    }

    private Map<String, Object> setClientResponseCookie(User user, HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", user);
        map.put("token", sessionId);
        Cookie cookie = new Cookie("token", sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24);
        response.addCookie(cookie);
        return map;
    }
}
