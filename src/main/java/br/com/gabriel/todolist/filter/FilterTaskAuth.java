package br.com.gabriel.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.gabriel.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {


    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get authentication credentials
        var authorization = request.getHeader("Authorization");


        var authEncoded = authorization.substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecode);

        String[] credentials = authString.split(":");

        var username = credentials[0];
        var password = credentials[1];
        System.out.println(username + " " + password);

        // Validate User

        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(401);
        }
        // Validate Password
        var passwordVarify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (!passwordVarify.verified) {
            response.sendError(401);
        }

        // Go away

        filterChain.doFilter(request, response);
    }

}
