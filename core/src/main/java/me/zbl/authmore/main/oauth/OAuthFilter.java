/*
 * Copyright 2019 ZHENG BAO LE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zbl.authmore.main.oauth;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-01
 */
public abstract class OAuthFilter extends OncePerRequestFilter {

    public void sendError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "oauth unauthorized");
    }

    public void sendError(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    public void sendError(HttpServletResponse response, String message, int status) throws IOException {
        response.sendError(status, message);
    }
}