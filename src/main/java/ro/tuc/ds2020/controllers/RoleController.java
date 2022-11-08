package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.RoleDTO;
import ro.tuc.ds2020.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> saveRole(@RequestBody RoleDTO role) {
        final Long id = roleService.saveRole(role);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getRoles() {
        final List<RoleDTO> roles = roleService.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
