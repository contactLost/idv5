package com.cntclst.idv5.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

    public String createUser(User user) throws Exception {

        User user1 = userRepository.getUserByUsername(user.getUsername());

        if (user1 != null) {
            throw new Exception("A user exist with this username");
        }

        userRepository.save(user);
        return "Success";
    }

    public void updatePassword(String username, String pass){
        User user1 = userRepository.getUserByUsername(username);
        userRepository.updatePasswordById(pass,user1.getId());
    }

    public void deleteUser( int id){

        userRepository.deleteById(id);
    }

}
