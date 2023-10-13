package br.com.rodrigovianna.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rodrigovianna.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final var path = request.getServletPath();
    if (!path.startsWith("/tasks")) {
      filterChain.doFilter(request, response);
      return;
    }

    final var authorization = request.getHeader("Authorization");
    if (authorization == null || authorization.isBlank()) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    final var token = authorization.substring(6);
    if (token.isBlank()) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    final var authDecoded = new String(Base64.getDecoder().decode(token));
    final var authArray = authDecoded.split(":");
    final var username = authArray[0];

    final var user = this.userRepository.findByUsername(username);
    if (user == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    final var password = authArray[1];
    if (!BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    request.setAttribute("userId", user.getId());
    filterChain.doFilter(request, response);
  }

}
