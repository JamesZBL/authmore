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

import java.util.Set;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-26
 */
public class TokenCheckResponse {

    private String userId;
    private Set<String> scope;
    private long exp;
    private String client_id;
    private Set<String> authorities;
    private Set<String> resourceIds;

    public TokenCheckResponse() {}

    public TokenCheckResponse(
            String userId,
            Set<String> scope,
            long exp,
            String client_id,
            Set<String> authorities,
            Set<String> resourceIds) {
        this.scope = scope;
        this.exp = exp;
        this.client_id = client_id;
        this.authorities = authorities;
        this.resourceIds = resourceIds;
    }

    public TokenCheckResponse(AccessTokenBinding tokenBinding, Set<String> authorities, Set<String> resourceIds) {
        this(tokenBinding.getUserId(),
                tokenBinding.getScopes(),
                tokenBinding.getExpire(),
                tokenBinding.getClientId(),
                authorities,
                resourceIds);
    }

    public Set<String> getScope() {
        return scope;
    }

    public long getExp() {
        return exp;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
