package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.restClient.RestClient;
import web.service.UserServiceImp;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
public class RestControllers {

    @Autowired
    private RestClient restClient;

    @GetMapping("/api/authenticate")
    public ResponseEntity<User> authenticate(Principal principal) {
        User authorizedUser = restClient.findUserByEmail(principal.getName());
        return ResponseEntity.ok(authorizedUser);
    }

    @GetMapping("/admin/allusers")
    public ResponseEntity <List<User>> getAllUsers(){
        List<User> users = restClient.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/admin/new")
    public void addUser(@RequestBody User user){

        Set<Role> roles = user.getRoles();
        if(user.getIsAdmin()){
            Role role = new Role();
            role.setName("ADMIN");
            roles.add(role);
        }
        if(user.getIsUser()){
            Role role = new Role();
            role.setName("USER");
            roles.add(role);
        }
        user.setRoles(roles);
        restClient.addUser(user);
    }

    @PostMapping("/admin/updateuser")
    public void updateUser(@RequestBody User user){
        Set<Role> roles = user.getRoles();
        if(user.getIsAdmin()){
            Role role = new Role();
            role.setName("ADMIN");
            roles.add(role);
        }
        if(user.getIsUser()){
            Role role = new Role();
            role.setName("USER");
            roles.add(role);
        }
        user.setRoles(roles);
        restClient.updateUser(user);
    }

    @DeleteMapping("/admin/deleteuser/{id}")
    public void deleteUser(@PathVariable Long id){
        restClient.deleteUser(id);
    }
}
