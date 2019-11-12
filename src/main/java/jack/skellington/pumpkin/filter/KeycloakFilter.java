package jack.skellington.pumpkin.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.adapters.servlet.KeycloakOIDCFilter;
import org.springframework.stereotype.Component;

@Component
public class KeycloakFilter extends KeycloakOIDCFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getMethod().equals("PATCH")) {
			super.doFilter(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}
}
