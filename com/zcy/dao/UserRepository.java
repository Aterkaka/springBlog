package com.zcy.dao;

import com.zcy.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: 张诚耀
 * @create: 2021-02-23 15:27
 */

public interface UserRepository extends JpaRepository <User, Long>{

    User findByUsernameAndPassword(String username, String password);
}
