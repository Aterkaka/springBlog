package com.zcy.service;

import com.zcy.po.Message;

import java.util.List;

/**
 * @author: 张诚耀
 * @create: 2021-03-17
 */

public interface MessageService {
    List<Message> listMessage();
    Message saveMessage(Message message);
}
