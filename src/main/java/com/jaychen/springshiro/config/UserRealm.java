package com.jaychen.springshiro.config;

import com.jaychen.springshiro.pojo.User;
import com.jaychen.springshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//  interact with database to do authentication and authorization
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("execution of doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        simpleAuthorizationInfo.addStringPermission(user.getPerms());

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // get username and pwd from database

//        String name = "root";
//        String password="123";
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.queryUserByName(token.getUsername());
        if(user==null){
            return null; // throw exception of unknownAccountException
        }

        if(!token.getUsername().equals(user.getName())){
            return null; // throw exception of unknownAccountException
        }

        System.out.println("execution of doGetAuthenticationInfo");
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPwd(),"");
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("loginUser",user);

        return authenticationInfo;// pass that user(1st para) from DB as principal to do authorization
        // shiro will do pwd authentication and pwd is encrypted(customizable)
    }
}
