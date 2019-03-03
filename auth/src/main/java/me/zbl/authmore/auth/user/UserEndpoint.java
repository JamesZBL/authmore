package me.zbl.authmore.auth.user;

import me.zbl.authmore.auth.AuthController;
import me.zbl.authmore.auth.DataWrapper;
import me.zbl.authmore.core.UserDetails;
import me.zbl.reactivesecurity.common.entity.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author ZHENG BAO LE
 * @since 2019-01-28
 */
@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('SA')")
public class UserEndpoint extends AuthController {

    private UserDetailsRepo users;

    public UserEndpoint(UserDetailsRepo users, PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
        this.users = users;
    }

    @GetMapping
    public DataWrapper<UserDetails> all() {
        List<UserDetails> all = users.findAllByOrderByIdDesc();
        return new DataWrapper<>(all);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody UserDetails user) {
        encodePassword(user);
        users.save(user);
        return success();
    }

    @GetMapping("/{id}")
    public UserDetails byId(@PathVariable("id") String id) {
        return users.findById(id).orElse(null);
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody UserDetails user) {
        String id = user.getId();
        Optional<UserDetails> original = users.findById(id);
        original.orElseThrow(IllegalArgumentException::new);
        String input = user.getPassword();
        if (isEmpty(input))
            original.ifPresent(o -> user.setPassword(o.getPassword()));
        else if (!input.startsWith("{"))
            encodePassword(user);
        users.save(user);
        return success();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        users.deleteById(id);
        return success();
    }

    @GetMapping("/exist")
    public Map exist(@RequestParam("username") String name) {
        Optional<UserDetails> find = users.findByUsername(name);
        if (find.isPresent())
            return exist(true);
        return exist(false);
    }

    @DeleteMapping()
    public ResponseEntity deleteBatch(@RequestBody List<String> ids) {
        users.deleteByIdIn(ids);
        return success();
    }
}
