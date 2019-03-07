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
package me.zbl.authmore.main.client;

import me.zbl.authmore.main.oauth.OAuthProperties.*;
import me.zbl.authmore.main.oauth.RequestUtil;
import me.zbl.reactivesecurity.common.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static me.zbl.authmore.main.oauth.OAuthProperties.*;

/**
 * @author ZHENG BAO LE
 * @since 2019-03-07
 */
public class AuthorizationTemplate implements AuthorizationOperations {

    private final String authorizeUrl;
    private final String clientId;
    private final String redirectUrl;

    public AuthorizationTemplate(ClientProperties client) {
        this.authorizeUrl = client.getAuthorizeUri();
        this.clientId = client.getClientId();
        this.redirectUrl = client.getRedirectUri();
    }

    @Override
    public void redirectToUserAuthorize(HttpServletResponse response, ResponseTypes type, String scope)
            throws IOException {
        Assert.notEmpty(redirectUrl, "redirect url is required");
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_RESPONSE_TYPE, type.getName());
        params.put(PARAM_CLIENT_ID, clientId);
        params.put(PARAM_REDIRECT_URI, redirectUrl);
        params.put(PARAM_SCOPE, scope);
        response.sendRedirect(authorizeUrl + "?" + RequestUtil.queryStringOf(params));
    }
}
