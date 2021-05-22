package com.example.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String POSTS = API_PATH + "/posts";
    public static final String THREADS = API_PATH + "/threads";
    public static final String BOARDS = API_PATH + "/boards";
    public static final String MEDIA = API_PATH + "/media";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";

    public static final String APPLICATION_SOCKET_PREFIX = API_PATH + "/chat";
    public static final String STOMP_WEB_SOCKET_ENDPOINT = API_PATH + "/websocket";
    public static final String BROKER_PREFIX = API_PATH + "/user-chat";

    public static final String MESSAGE = "/message";

    public static final String ENTITY = "/{id}";

    public static final String FILTERED = "/filtered";
}
