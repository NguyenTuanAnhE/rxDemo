package com.rxandroid.anhnt.rxdemo.utils;

public interface Constants {

    interface ApiRequest {
        String HOST = "https://api-v2.soundcloud.com/";
        String HOST_SEARCH = "http://api.soundcloud.com/tracks";
        String PARAMETER_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
        String CLIENT_ID = "client_id";
        String PARAMETER_STREAM = "stream";
        String API_KEY = "a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU";
        String LIMIT = "limit";
        String OFFSET = "offset";
        String REQUEST_METHOD = "GET";
        String ERROR_MESSAGE = "Error";
        int READ_TIMEOUT = 5000;
        int CONNECT_TIMEOUT = 5000;
        String NULL = "null";
        int LIMIT_VALUE = 20;
        int OFFSET_VALUE = 0;
        String LARGE = "-large";
        String CROP = "-crop";
        String SEARCH_FILTER = "?filter=public";
        String PARAMETER_SEARCH = "q";
    }
}
