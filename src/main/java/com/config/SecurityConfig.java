package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//添加@EnableGlobalMethodSecurity注解开启Spring方法级安全
//prePostEnabled属性决定Spring Security的前注解是否可用@PreAuthorize,@PostAuthorize等注解,设置为true
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {    
		@Override
	    public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
	        .antMatchers("/login")
	        .permitAll()
	        .antMatchers("/admin/index")
	        .hasRole("admin")//指定权限为ADMIN才能访问
	        .antMatchers("/person")
	        .hasAnyRole("admin","user")//指定多个权限都能访问
	        .anyRequest() //任何其它请求
	        .authenticated() //都需要身份认证
	        .and()
	        .formLogin() //使用表单认证方式
	        .loginPage("/login")//配置默认登录入口
	        .defaultSuccessUrl("/",true)//配置登录成功后页面
			.failureUrl("/login?error=true")
	        .and()
	        .csrf().disable();//20211205提交测试
	    }
		
		  @Override
		    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		        auth.inMemoryAuthentication()
		                .withUser("user").roles("admin").password(new BCryptPasswordEncoder().encode("123"))
		                .and()
		                .withUser("lisi").roles("user").password(new BCryptPasswordEncoder().encode("123"));
		    }
		    @Bean
		    PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }
		  
		  @Override
		    public void configure(WebSecurity web) throws Exception {
		        web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/locales/**", "/webfonts/**");
		    }
}
