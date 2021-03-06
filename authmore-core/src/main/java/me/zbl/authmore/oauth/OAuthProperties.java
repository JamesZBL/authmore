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
package me.zbl.authmore.oauth;

import java.util.Arrays;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-14
 */
public interface OAuthProperties {

    long CODE_VALIDITY_SECONDS = 5 * 60;
    long DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS = 24 * 60 * 60;
    String TOKEN_CLIENT_ID = "client_id";
    String TOKEN_USER_ID = "user_id";
    String TOKEN_SCOPES = "scopes";
    String TOKEN_AUTHORITIES = "authorities";
    String TOKEN_RESOURCE_IDS = "resource_ids";
    String TOKEN_EXPIRE_AT = "expire_at";
    String PARAM_CLIENT_ID = "client_id";
    String PARAM_CLIENT_SECRET = "client_secret";
    String PARAM_USERNAME = "username";
    String PARAM_PASSWORD = "password";
    String PARAM_SCOPE = "scope";
    String PARAM_GRANT_TYPE = "grant_type";
    String PARAM_CODE = "code";
    String PARAM_REFRESH_TOKEN = "refresh_token";
    String PARAM_RESPONSE_TYPE = "response_type";
    String PARAM_REDIRECT_URI = "redirect_uri";
    String SCOPE_USER_DETAILS = "PROFILE";
    String REQUEST_SCOPES = "REQUEST_SCOPES";
    String REQUEST_AUTHORITIES = "REQUEST_AUTHORITIES";
    String REQUEST_RESOURCE_IDS = "REQUEST_RESOURCE_IDS";
    String REQUEST_USER_ID = "REQUEST_USER_ID";
    String KEY_PREFIX_CODE_BINDING = "authmore:authorization:code";
    String KEY_PREFIX_ACCESS_TOKEN_BINDING = "authmore:authorization:access-token";
    String KEY_PREFIX_REFRESH_TOKEN_BINDING = "authmore:authorization:refresh-token";

    enum GrantTypes {

        AUTHORIZATION_CODE("authorization_code"),
        IMPLICIT("implicit"),
        PASSWORD("password"),
        CLIENT_CREDENTIALS("client_credentials"),
        REFRESH_TOKEN("refresh_token");

        private final String name;

        GrantTypes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static GrantTypes eval(String name) {
            return Arrays.stream(GrantTypes.values())
                    .filter(t -> t.getName().equals(name)).findFirst()
                    .orElseThrow(() -> new OAuthException(OAuthException.UNSUPPORTED_GRANT_TYPE));
        }
    }

    enum ResponseTypes {

        CODE("code"),
        TOKEN("token");

        private final String name;

        ResponseTypes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static ResponseTypes eval(String name) {
            return Arrays.stream(ResponseTypes.values())
                    .filter(t -> t.getName().equals(name)).findFirst()
                    .orElseThrow(() -> new OAuthException(OAuthException.UNSUPPORTED_RESPONSE_TYPE));
        }
    }

    enum RequireTypes {

        ALL, ANY
    }
}
