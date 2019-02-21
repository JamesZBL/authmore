/*
 * Copyright 2019 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.authmore;

import me.zbl.reactivesecurity.auth.client.ClientDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * @since 2019-02-19
 */
@WebFilter(urlPatterns = {"/oauth/token"})
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private ClientDetailsRepository clients;
    private PasswordEncoder passwordEncoder;

    public TokenAuthenticationFilter(ClientDetailsRepository clients, PasswordEncoder passwordEncoder) {
        this.clients = clients;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientId = request.getParameter("client_id");
        String clientSecret = request.getParameter("client_secret");
        if (isEmpty(clientId) || isEmpty(clientSecret)) {
            sendUnauthorized(response);
            return;
        }
        Optional<ClientDetails> find = clients.findByClientId(clientId);
        if (!find.isPresent()) {
            sendUnauthorized(response);
            return;
        }
        ClientDetails client = find.get();
        if (!passwordEncoder.matches(clientSecret, client.getPassword())) {
            sendUnauthorized(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}