package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.dto.UserDto;
import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.request.CreateRequestUser;
import com.dailycodework.dreamshops.request.UpdateRequestUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
          return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(CreateRequestUser user) {
        return Optional.of(user)
                .filter(foundUser->!userRepository.existsByEmail(foundUser.getEmail()))
                .map(requestUser->{
                    User newUser=new User();
                    newUser.setFirstName(requestUser.getFirstName());
                    newUser.setLastName(requestUser.getLastName());
                    newUser.setEmail(requestUser.getEmail());
                    newUser.setPassword(requestUser.getPassword());
                    return userRepository.save(newUser);
                }).orElseThrow(()->new AlreadyExistsException(user.getEmail() + " already exists"));
    }

    @Override
    public User updateUser(UpdateRequestUser user, Long userId) {
        return userRepository.findById(userId)
                .map((existingUser)->{
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
       userRepository.findById(userId).ifPresentOrElse(
               userRepository::delete,
               ()-> {
            throw new ResourceNotFoundException("User not found");
        }
       );
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto convertToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
