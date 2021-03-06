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
package me.zbl.authmore.platform.authorization;

import me.zbl.authmore.authorization.AbstractCodeManager;
import me.zbl.authmore.authorization.CodeBinding;
import me.zbl.authmore.oauth.OAuthException;

import static me.zbl.authmore.oauth.OAuthException.INVALID_CODE;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-18
 */
public class RedisCodeManager extends AbstractCodeManager {

    private final CodeRepository authorizationCodes;

    public RedisCodeManager(CodeRepository authorizationCodes) {
        super();
        this.authorizationCodes = authorizationCodes;
    }

    @Override
    public void saveCode(CodeBinding codeBinding) {
        authorizationCodes.save(codeBinding);
    }

    @Override
    public CodeBinding getCodeDetails(String clientId, String code) {
        return authorizationCodes.findById(code)
                .orElseThrow(() -> new OAuthException(INVALID_CODE));
    }

    @Override
    public void expireCode(String code) {
        authorizationCodes.deleteById(code);
    }
}
