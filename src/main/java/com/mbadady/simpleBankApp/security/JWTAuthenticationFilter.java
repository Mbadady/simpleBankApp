package com.mbadady.simpleBankApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserServiceDetails userServiceDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String token = getJWTFromHttpRequest(request);

//        Validate Token

        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
//      get Username from token
            String username = jwtTokenProvider.generateUsernameFromJWT(token);

//            load user associated with token

            UserDetails userDetails = userServiceDetails.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);

    }

    private String getJWTFromHttpRequest(HttpServletRequest request){
       String bearerToken =  request.getHeader(SecurityConstant.JWT_HEADER);

       if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
        return bearerToken.substring(7);
       }
       return null;
    }
}
