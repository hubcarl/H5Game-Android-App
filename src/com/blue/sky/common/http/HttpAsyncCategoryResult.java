package com.blue.sky.common.http;


import com.blue.sky.h5.game.category.Category;

import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public interface HttpAsyncCategoryResult {

  void process(List<Category> list);

}
