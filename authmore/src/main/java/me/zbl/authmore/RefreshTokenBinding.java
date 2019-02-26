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

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

import static me.zbl.authmore.OAuthProperties.KEY_PREFIX_REFRESH_TOKEN_BINDING;

/**
 * @author JamesZBL
 * @since 2019-02-26
 */
@RedisHash(value = KEY_PREFIX_REFRESH_TOKEN_BINDING)
public class RefreshTokenBinding {

    @Id
    private final String refreshToken;
    private final String clientId;
    private final Set<String> scopes;
    private final String userId;

    public RefreshTokenBinding(String refreshToken, String clientId, Set<String> scopes, String userId) {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.scopes = scopes;
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public String getUserId() {
        return userId;
    }
}
