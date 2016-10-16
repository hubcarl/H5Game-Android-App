package com.blue.sky.h5.game.category;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/8/14.
 */
public class Category implements Serializable {

    public Category(){

    }

    public Category(int categoryId, String title){
        this.categoryId1 = categoryId;
        this.title1 = title;
    }

    public int categoryId1;
    public int categoryId2;

    public String title1;
    public String description1;

    public String title2;
    public String description2;
}
