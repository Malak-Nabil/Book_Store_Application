package com.bookstore;

public class AdminManager {
    private static Admin admin = new Admin("admin", "admin@gmail.com", "admin");

    public static Admin getAdmin() {
        return admin;
    }
}
