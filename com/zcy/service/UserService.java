package com.zcy.service;

import com.zcy.po.User;

/**
 * @author: 张诚耀
 * @create: 2021-02-23 15:09
 */

public interface UserService {

    User checkUser(String username, String password);

    User registerUser(String username);


}
