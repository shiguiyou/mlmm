package com.wanquan.mlmmx.mlmm.kotlin;

import com.example.administrator.mlmmx.kotlin.AppConfig;
import com.wanquan.mlmmx.mlmm.kotlin.rsa.RSAUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class Utils {

    public static String getParamsStr(HashMap<String, String> params) {
        String result = "";
        if (params != null) {
            Iterator<Entry<String, String>> i = params.entrySet().iterator();
            while (i.hasNext()) {
                Entry<String, String> o = i.next();
                String key = o.getKey();
                result = result + key + "=" + o.getValue() + ";";
            }
        }
        return result;
    }

    public static String encryptParam(HashMap hashMap) {
        String Sign = SignUtils.sign(AppConfig.RSA_SIGN,
                AppConfig.RSA_PRIVATE_KEY);
        hashMap.put("sign", URLEncoder.encode(Sign));
        String encrypt = RSAUtils
                .encrypyByPublicKey(Utils.getParamsStr(hashMap).getBytes(), AppConfig.RSA_PUBLIC_KEY);
        return encrypt;
    }

}
