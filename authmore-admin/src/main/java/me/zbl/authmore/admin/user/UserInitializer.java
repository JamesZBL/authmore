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
package me.zbl.authmore.admin.user;

import me.zbl.authmore.UserDetails;
import me.zbl.authmore.common.RandomSecret;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@Component
public class UserInitializer implements SmartInitializingSingleton {

    private UserDetailsRepo users;
    private PasswordEncoder passwordEncoder;

    public UserInitializer(UserDetailsRepo users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterSingletonsInstantiated() {
        String randomPassword = RandomSecret.create();
        String rootUsername = "root";
        UserDetails user = users.findByUsername(rootUsername).orElse(
                new UserDetails("root", "SA"))
                .setUserPassword(passwordEncoder.encode(randomPassword));
        try {
            users.save(user);
        } catch (DuplicateKeyException ignored) {
        }

        System.out.println("\n\nAuthmore root password: " + randomPassword + "\n\n");
    }
}
