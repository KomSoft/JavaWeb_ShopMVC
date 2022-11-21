package com.komsoft.shopmvc.form;

import javax.servlet.http.HttpSession;

public class Header {
    public static final String SITE_ROOT = "ShopMVC-1.0-SNAPSHOT";
    public static final String PAGE_ROOT = "WEB-INF/views/";
    public static final String AUTHORIZED_PAGE_ROOT = PAGE_ROOT + "authorized/";
    public static final String LOGOUT_KEY = "logoutKey";
    public static final String LOGOUT_HREF = " - <a href='/" + SITE_ROOT + "/login?" + LOGOUT_KEY + "'>Logout</a>";
    public static final String AUTHENTICATED_USER_KEY = "authenticatedUser";
    public static final String MESSAGE = "message";
    public static final String LOGIN_MESSAGE = "loginMessage";
    public static final String LOGIN_PAGE = "loginform.jsp";
    public static final String REGISTRATION_PAGE = "registration.jsp";
    public static final String LOGIN_SUCCESS_PAGE = "loginsuccess.jsp";
    public static final String LOGIN_WELCOME_PAGE = "welcomeback.jsp";
    public static final String ERROR_PAGE = "errorpage.jsp";
    public static final String INFO_PAGE = "infopage.jsp";
    public static final String SECRET_PAGE = "secretpage.jsp";

    public static void setLogoutMenu(HttpSession session) {
        session.setAttribute(Header.LOGOUT_KEY, Header.LOGOUT_HREF);
    }
}
