package me.bk.community.config.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import me.bk.community.config.security.dto.LoginMember;
import me.bk.community.config.security.user.AuthenticationPrincipal;
import me.bk.community.config.security.user.UserDetailsImpl;

/**
 * @author : byungkyu
 * @date : 2021/02/10
 * @description :
 **/
public class AuthenticationPrincipalResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof UsernamePasswordAuthenticationToken) {
			UserDetailsImpl user = (UserDetailsImpl)authentication.getPrincipal();
			return LoginMember.builder()
				.id(user.getId())
				.email(user.getUsername())
				.nickName(user.getNickName())
				.build();
		}
		return new LoginMember();
	}
}
