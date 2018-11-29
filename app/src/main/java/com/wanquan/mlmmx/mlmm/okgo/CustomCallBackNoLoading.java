package com.wanquan.mlmmx.mlmm.okgo;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.wanquan.mlmmx.mlmm.utils.JSONUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by 芽芽先森 on 16/11/14.
 * 邮箱：763836746@qq.com
 */

public abstract class CustomCallBackNoLoading<T> extends AbsCallback<T> {

    Context mContext;

    public CustomCallBackNoLoading() {
    }

    public CustomCallBackNoLoading(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
//        Log.i("kkkkkkk1","获取的数据-->"+response.body().toString());
        Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }else{
//            L.i("不能直接用集合");
        }
//        return  JsonUtil.fromJson(response.body().string(), entityClass);
        return JSONUtils.parseToJavaBean(response.body().string(), entityClass);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
//        StyledDialog.dismissLoading();
        Log.i("kkkkkkk1","请求数据出错"+e.toString());
        e.printStackTrace();
    }
}
