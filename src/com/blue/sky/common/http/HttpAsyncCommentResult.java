package com.blue.sky.common.http;

import com.blue.sky.common.entity.Comment;

import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public interface HttpAsyncCommentResult {

  void process(List<Comment> list);

}
