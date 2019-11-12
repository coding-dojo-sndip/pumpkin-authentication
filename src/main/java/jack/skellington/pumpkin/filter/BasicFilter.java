package jack.skellington.pumpkin.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

//@Component Pour l'activer : il faut désactiver le KeycloakFilter
public class BasicFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authorization = httpRequest.getHeader("Authorization");
		if (httpRequest.getMethod().equals("PATCH") && authorization != null && authorization.startsWith("Basic")) {
			String credentialsBaseh64 = authorization.split(" ")[1];
			String[] idepMdp = new String(Base64.getDecoder().decode(credentialsBaseh64)).split(":");
			// En situation plus réelle la vérification se fait via une requete BIND sur un
			// ldap
			if (idepMdp[0].equals("admin") && idepMdp[1].equals("admin")) {
				HttpServletRequest newRequest = new HttpServletRequestWrapper(httpRequest) {
					@Override
					public Principal getUserPrincipal() {
						return () -> "admin";
					}

					@Override
					public String getRemoteUser() {
						return "admin";
					}

					@Override
					public boolean isUserInRole(String role) {
						return role.equals("admin");
					}
				};
				chain.doFilter(newRequest, response);
				return;
			}

		}
		chain.doFilter(request, response);
	}

}
