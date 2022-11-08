package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.BusinessException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.mappers.DeviceMapper;
import ro.tuc.ds2020.dtos.mappers.UserMapper;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Role;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.RoleRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DeviceRepository deviceRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, DeviceRepository deviceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.deviceRepository = deviceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Long saveUser(UserDTO user, Long roleId) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User savedUser = UserMapper.INSTANCE.dtoToModel(user);
        savedUser.setPassword(encodedPassword);
        final Role role = roleRepository.findById(roleId).orElseThrow(() -> {
            throw new BusinessException("Role not found!");
        });
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        savedUser.setRoles(roles);
        userRepository.save(savedUser);

        return savedUser.getId();
    }

    @Override
    public UserDTO getUserById(Long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("User not found!");
        });

        return UserMapper.INSTANCE.modelToDto(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper.INSTANCE::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO user) {
        final User userById = userRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("User not found!");
        });

        final String firstname = user.getFirstname();
        final String lastname = user.getLastname();
        final String email = user.getEmail();
        final String password = user.getPassword();
        if (null != firstname) {
            userById.setFirstname(firstname);
        }
        if (null != lastname) {
            userById.setLastname(lastname);
        }
        if (null != email) {
            userById.setEmail(email);
        }
        if (null != password) {
            userById.setPassword(passwordEncoder.encode(password));
        }
        User savedUser = userRepository.save(userById);

        return UserMapper.INSTANCE.modelToDto(savedUser);
    }

    @Override
    public Long deleteUser(Long id) {
        final User userById = userRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("User not found!");
        });
        userById.getDevices()
                .forEach(device -> device.setUser(null));

        userRepository.delete(userById);
        return id;
    }

    @Override
    public UserDTO assignRoleToUser(Long userId, Long roleId) {
        final User userById = userRepository.findById(userId).orElseThrow(() -> {
            throw new BusinessException("User not found!");
        });
        final Role roleById = roleRepository.findById(roleId).orElseThrow(() -> {
            throw new BusinessException("Role not found!");
        });

        Set<Role> roles = userById.getRoles();
        roles.add(roleById);
        userById.setRoles(roles);
        User savedUser = userRepository.save(userById);

        return UserMapper.INSTANCE.modelToDto(savedUser);
    }

    @Override
    public Long assignDeviceToUser(Long userId, Long deviceId) {
        final User userById = userRepository.findById(userId).orElseThrow(() -> {
            throw new BusinessException("User not found!");
        });
        final Device deviceById = deviceRepository.findById(deviceId).orElseThrow(() -> {
            throw new BusinessException("Device not found!");
        });

        if (null != deviceById.getUser())
            throw new BusinessException("A user has already this device");
        deviceById.setUser(userById);
        deviceRepository.save(deviceById);
        return userId;
    }

    @Override
    public List<DeviceDTO> getUserDevices(Long id) {
        final User userById = userRepository.findById(id).orElseThrow(() -> {
            throw new BusinessException("User not found!");
        });
        return userById.getDevices().stream()
                .map(DeviceMapper.INSTANCE::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUserWithEmailPresent(String email) {
        final Optional<User> userByEmail = userRepository.findByEmail(email);
        return userByEmail.isPresent();
    }
}

