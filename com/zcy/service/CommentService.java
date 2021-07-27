package com.zcy.service;

import com.zcy.po.Comment;

import java.util.List;

/**
 * @author: 张诚耀
 * @create: 2021-03-07
 */

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

//    Integer getCommentTotal(Long commentid,Long blogid);
}
