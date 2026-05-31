package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 安全配置类
 * 配置认证规则、登录页面、密码加密等安全相关配置
 *
 * @author Auto Generated
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 用户详情服务接口，Spring Security会自动注入实现类
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 配置HTTP安全策略
     * 定义哪些URL需要认证，哪些可以匿名访问
     *
     * @param http HttpSecurity对象
     * @throws Exception 配置异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 登录页、注册页、静态资源允许所有人访问
                .antMatchers("/login", "/register", "/css/**", "/js/**", "/img/**", "/webjars/**", "/h2-console/**")
                .permitAll()
                // 其他所有请求都需要身份认证
                .anyRequest()
                .authenticated()
                .and()
                // 配置表单登录
                .formLogin()
                .loginPage("/login")                    // 自定义登录页面路径
                .defaultSuccessUrl("/", true)          // 登录成功后跳转到首页
                .failureUrl("/login?error=true")        // 登录失败后跳转到登录页
                .and()
                // 配置退出登录
                .logout()
                .logoutSuccessUrl("/login?logout=true") // 退出成功后跳转到登录页
                .permitAll()
                .and()
                // 禁用CSRF保护（方便表单提交，生产环境建议启用）
                .csrf()
                .ignoringAntMatchers("/login", "/register", "/h2-console/**")
                .and()
                // 配置HTTP响应头
                .headers()
                .frameOptions()
                .sameOrigin();
    }

    /**
     * 配置认证管理器
     * 设置用户详情服务和密码加密器
     *
     * @param auth AuthenticationManagerBuilder对象
     * @throws Exception 配置异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 配置Web安全策略
     * 忽略某些静态资源的安全检查
     *
     * @param web WebSecurity对象
     * @throws Exception 配置异常
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/webjars/**", "/webfonts/**");
    }

    /**
     * 配置密码加密器
     * 使用BCrypt加密算法对密码进行加密和验证
     *
     * @return PasswordEncoder加密器实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
