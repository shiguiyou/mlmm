package com.wanquan.mlmmx.mlmm.okgo;

import android.content.Context;
import android.support.annotation.Nullable;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wangzeya on 16/11/10.
 */

public abstract class CustomCallBack<T> extends AbsCallback<T> {

    Context mContext;

    private CustomCallBack() {
    }

    public CustomCallBack(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
//        StyledDialog.buildLoading(mContext, mContext.getString(R.string.loading) ,false, false).show();

    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }else{
            L.i("error",false);
        }
        return  JsonUtil.fromJson(response.body().string(), entityClass);
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        L.i("网络请求执行完成",false);
//        StyledDialog.dismissLoading();
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        L.i("网络请求执行失败",false);
//        StyledDialog.dismissLoading();
        e.printStackTrace();
    }
}
