package com.zcy.dao;

import com.zcy.po.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: 张诚耀
 * @create: 2021-03-16
 */

public interface MessageRepository extends JpaRepository<Message,Long>{

    List<Message> findAllByParentMessageNull(Sort Sort);

    Message getMessageById(Long id);

    @Query("select count(id) from Message")
    Integer getMessageTotal();
}
