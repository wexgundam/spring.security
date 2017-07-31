package org.mose.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
public class IpFilter extends OncePerRequestFilter {
    private String targetRole;//目标角色
    private List<String> authorizedIpAddresses;//IP地址列表

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && targetRole != null) {
            boolean shouldCheck = false;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(targetRole)) {
                    shouldCheck = true;
                    break;
                }
            }
            if (shouldCheck && !authorizedIpAddresses.isEmpty()) {
                boolean authorized = false;
                for (String ipAddress : authorizedIpAddresses) {
                    if (request.getRemoteAddr().equals(ipAddress)) {
                        authorized = true;
                        break;
                    }
                }

                if (!authorized) {
                    throw new AccessDeniedException(
                            "Access has been denied for you IP address:" + request.getRemoteAddr());
                }
            }
        } else {
            System.out.println(
                    "The IPFilter should be placed after the user has been authenticated in the filter chain.");
        }
        filterChain.doFilter(request, response);
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public List<String> getAuthorizedIpAddresses() {
        return authorizedIpAddresses;
    }

    public void setAuthorizedIpAddresses(List<String> authorizedIpAddresses) {
        this.authorizedIpAddresses = authorizedIpAddresses;
    }
}
