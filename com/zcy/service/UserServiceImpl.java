package com.zcy.service;

import com.zcy.dao.UserRepository;
import com.zcy.po.User;
import com.zcy.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 张诚耀
 * @create: 2021-02-23 15:26
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {  //检查用户，通过username和password
        User user = userRepository.findByUsernameAndPassword(username,MD5Utils.code(password));
        return user;
    }

    @Override
    public User registerUser(String username) {
        return null;
    }


}
