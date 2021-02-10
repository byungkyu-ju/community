package me.bk.community.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import me.bk.community.auth.infra.JwtTokenProvider;
import me.bk.community.config.security.user.UserDetailsServiceImpl;

/**
 * @author : byungkyu
 * @date : 2021/02/09
 * @description :
 **/
public class JwtAuthTokenFilter extends BasicAuthenticationFilter {
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER_TYPE = "Bearer";

	private JwtTokenProvider jwtTokenProvider;
	private UserDetailsServiceImpl userDetailsServiceImpl;

	public JwtAuthTokenFilter(AuthenticationManager authenticationManager,
		JwtTokenProvider jwtTokenProvider,
		UserDetailsServiceImpl userDetailsServiceImpl) {
		super(authenticationManager);
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		Authentication authentication = extractAuthorizationHeader(request);

		if(authentication != null){
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}

	private Authentication extractAuthorizationHeader(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if(token == null){
			return null;
		}

		if(token.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())){
			String rawToken = token.substring(BEARER_TYPE.length()).trim();
			String email = getPayLoad(rawToken);
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}
		return null;
	}

	private String getPayLoad(String rawToken) {
		if(!jwtTokenProvider.validateToken(rawToken)){
			throw new IllegalArgumentException("invalid token");
		}
		return jwtTokenProvider.getPayload(rawToken);
	}
}
