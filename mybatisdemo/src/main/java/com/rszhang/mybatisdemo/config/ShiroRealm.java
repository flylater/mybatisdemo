package com.rszhang.mybatisdemo.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;

public class ShiroRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("token: " + authenticationToken);

        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //2. 从 UsernamePasswordToken 中来获取 username
        String username = token.getUsername();
        //3. 调用数据库的方法，从数据库中查询 username 对应的用户记录

        //4. 若用户不存在，则可以抛出异常

        //5. 根据用户信息的情况，决定是否需要抛出其他的异常

        //6. 根据用户的情况，来构建 对象并返回

        return null;
    }
}
