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
package me.zbl.authmore.clientsample;

import me.zbl.authmore.main.ClientRestTemplate;
import me.zbl.authmore.main.TokenResponse;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.zbl.authmore.main.RequestUtil.queryStringOf;

/**
 * @author JamesZBL
 * @since 2019-03-01
 */
@RestController
public class SampleEndpoint {

    private final ClientRestTemplate client;

    public SampleEndpoint(ClientRestTemplate client) {
        this.client = client;
    }

    @GetMapping
    public String password() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", "cartapp");
        params.put("client_secret", "123456");
        params.put("grant_type", "password");
        params.put("scope", "PROFILE");
        params.put("username", "james");
        params.put("password", "123456");
        String query = queryStringOf(params);
        TokenResponse token = client.postForObject(
                "http://localhost:8086/oauth/token" + "?" + query, null, TokenResponse.class);
        RestTemplate template = new RestTemplate();
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + token.getAccess_token());
            return execution.execute(request, body);
        };
        template.setInterceptors(Stream.of(interceptor).collect(Collectors.toList()));
        return template.getForObject("http://localhost:8011/", String.class);
    }
}
