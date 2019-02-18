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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author JamesZBL
 * created at 2019-02-18
 */
@Component
public class RedisCodeManager implements CodeManager {

    private static final String KEY_PREFIX = "authorize_code_";

    private RedisTemplate<String, String> redisTemplate;

    public RedisCodeManager(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveCodeBinding(ClientDetails client, String code) {
        redisTemplate.boundValueOps(buildKey(code)).set(client.getClientId(), CodeManager.codeValiditySeconds);
    }

    @Override
    public boolean isValidCode(String clientId, String code) {
        String find = redisTemplate.boundValueOps(buildKey(code)).get();
        return null != find && find.equals(clientId);
    }

    private String buildKey(String key) {
        return KEY_PREFIX + key;
    }
}