package com.example.tristan.android_projects;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Tristan on 2/12/18.
 */

public class RequestParams {
    private HashMap<String, String> params;
    private StringBuilder stringBuilder;

    public RequestParams() {
        params = new HashMap<>();
        stringBuilder = new StringBuilder();
    }

    public RequestParams addParam(String key, String value) throws UnsupportedEncodingException {
        params.put(key, URLEncoder.encode(value,"UTF-8"));

        return this;
    }

    public String getEncodedParams() {
        for (String key:params.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(key + "=" + params.get(key));
        }

        return stringBuilder.toString();
    }

    public String getEncodedURL(String url) {
        return url + "?" + getEncodedParams();
    }
}
