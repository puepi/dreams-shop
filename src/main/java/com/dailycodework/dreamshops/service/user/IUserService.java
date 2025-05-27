package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.request.CreateRequestUser;
import com.dailycodework.dreamshops.request.UpdateRequestUser;
import java.util.List;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateRequestUser user);
    User updateUser(UpdateRequestUser user, Long userId);
    void deleteUser(Long userId);
    User getUserByEmail(String email);
    UserDto convertToDto(User user);

    List<User> getAllUsers();
}
