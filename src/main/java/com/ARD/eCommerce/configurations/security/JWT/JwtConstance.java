package com.ARD.eCommerce.configurations.security.JWT;

public interface JwtConstance {
    public static final String BEARER_TOKEN = "Bearer ";
//    public static final long JWT_EXPIRATION = 10 * 60 * 500; 1 min
    public static final long JWT_EXPIRATION = 86400000;
    public static final String JWT_SECRET_KEY="924864a28358ec6f1df570175bbaec56d5944cb1ed30dfaf8565fbb367d32173";
    public static final String HEADER = "Authorization";
    public static String JWT_USER_ROLE_CLAIM_NAME = "roles";
    public static String JWT_USER_MAIL_CLAIM_NAME="mail";
    public static String JWT_USER_PERMISSION_CLAIM_NAME = "permissions";
    public static String JWT_USER_NAME_CLAIM_NAME = "username";
    public static String JWT_USER_ID_CLAIM_NAME="userId";
}
