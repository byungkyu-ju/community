package me.bk.community.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.bk.community.config.security.AuthenticationPrincipalResolver;

/**
 * @author : byungkyu
 * @date : 2021/02/10
 * @description :
 **/
@Configuration
public class CommonWebMvcConfigurer implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthenticationPrincipalResolver());
	}
}
