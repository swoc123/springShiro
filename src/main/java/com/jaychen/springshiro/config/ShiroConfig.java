package com.jaychen.springshiro.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // ShirofilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager1") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //set security manager
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //add shiro internal filter
        /*
        anon: don't need authentication to access
        authc: have to be authenticated to access
        user: have to possess Remember me function
        perms: have permission to access that certain file
        role: have to be that role to access
         */
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/add","anon");
        filterChainDefinitionMap.put("/user/update","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        shiroFilterFactoryBean.setLoginUrl("/login");


        return shiroFilterFactoryBean;
    }

    // DefaultWebSecurityManager
    @Bean("securityManager1")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("bad") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;

    }

    // New Realm Object
    @Bean(name="bad")
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
