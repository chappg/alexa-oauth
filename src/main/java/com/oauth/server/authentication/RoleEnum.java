package com.oauth.server.authentication;

public enum RoleEnum {
    ROLE_USER_ADMIN, //A role for administrators to manage clients and partners.
    ROLE_USER_CUSTOMER, //A role for customers
    ROLE_CLIENT_ADMIN, //A role for an internal administration OAuth client.
    ROLE_CLIENT_CUSTOMER //A role for a external OAuth client.
}
