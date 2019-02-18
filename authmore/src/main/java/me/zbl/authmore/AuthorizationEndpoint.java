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
import me.zbl.reactsecurity.common.RandomPassword;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static me.zbl.authmore.OAuthException.UNSUPPORTED_RESPONSE_TYPE;
import static me.zbl.authmore.SessionProperties.CURRENT_CLIENT;
import static me.zbl.authmore.SessionProperties.LAST_STATE;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author JamesZBL
 * created at 2019-02-14
 */
@Controller
public class AuthorizationEndpoint {

    private AuthenticationManager authenticationManager;
    private CodeManager codeManager;

    public AuthorizationEndpoint(AuthenticationManager authenticationManager, CodeManager codeManager) {
        this.authenticationManager = authenticationManager;
        this.codeManager = codeManager;
    }

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam("client_id") String clientId,
            @RequestParam("response_type") String responseType,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "state", required = false) String state,
            HttpSession session,
            Model model,
            HttpServletResponse response) throws IOException {
        ClientDetails client = authenticationManager.clientValidate(clientId, redirectUri, scope);
        if(!"code".equals(responseType))
            throw new OAuthException(UNSUPPORTED_RESPONSE_TYPE);
        if (client.isAutoApprove()) {
            String code = RandomPassword.create();
            codeManager.saveCodeBinding(client, code);
            String location = String.format("%s?code=%s&state=%s", redirectUri, code, state);
            response.sendRedirect(location);
        }
        if (!isEmpty(state)) {
            session.setAttribute(LAST_STATE, state);
        }
        session.setAttribute(CURRENT_CLIENT, client);
        model.addAttribute("client", client);
        return "authorize";
    }

    //    @PostMapping("/")
}