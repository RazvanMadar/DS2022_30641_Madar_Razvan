package ro.tuc.ds2020.services;


import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;

import java.util.List;


public interface UserService {
    Long saveUser(UserDTO user, Long roleId);
    List<UserDTO> getUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO user);
    Long deleteUser(Long id);
    UserDTO assignRoleToUser(Long userId, Long roleId);
    Long assignDeviceToUser(Long userId, Long deviceId);
    List<DeviceDTO> getUserDevices(Long id);
    boolean isUserWithEmailPresent(String email);
}
