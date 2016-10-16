package com.blue.sky.common.http;

import com.blue.sky.common.entity.GameInfo;

import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public interface HttpAsyncResult{

  void process(List<GameInfo> list);

}
