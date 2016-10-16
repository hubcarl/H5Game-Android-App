package com.blue.sky.common.http;

import com.blue.sky.common.utils.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/7/27.
 */
public class BaseHttpAsyncResult<T> {

    protected Action.RESULT result;

    public BaseHttpAsyncResult(){

    }

    public BaseHttpAsyncResult(Action.RESULT result){
        this.result = result;

    }

    public List<T> baseProcess(String response)
    {
       if(Action.RESULT.DataItem.equals(result)){
           return (List<T>)JsonHelper.parse(response);
       }else{
          return new ArrayList<T>();
       }
    }
}
