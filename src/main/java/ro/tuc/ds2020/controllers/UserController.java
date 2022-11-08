package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user, @RequestParam(defaultValue = "1") Long roleId) {
        Long id = userService.saveUser(user, roleId);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<?> isUserWithEmailPresent(@RequestParam String email) {
        boolean isUserPresent = userService.isUserWithEmailPresent(email);
        return new ResponseEntity<>(isUserPresent, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        final UserDTO userById = userService.getUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        final UserDTO userDTO = userService.updateUser(id, user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Long userId = userService.deleteUser(id);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PutMapping("/assign-role")
    public ResponseEntity<?> assignRoleToUser(@RequestParam Long userId, @RequestParam Long roleId) {
        final UserDTO userDTO = userService.assignRoleToUser(userId, roleId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/assign-device")
    public ResponseEntity<?> assignDeviceToUser(@RequestParam Long userId, @RequestParam Long deviceId) {
        final Long id = userService.assignDeviceToUser(userId, deviceId);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{id}/devices")
    public ResponseEntity<?> getUserDevices(@PathVariable Long id) {
        final List<DeviceDTO> userDevices = userService.getUserDevices(id);
        return new ResponseEntity<>(userDevices, HttpStatus.OK);
    }
}
