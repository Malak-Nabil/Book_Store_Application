package com.bookstore;

import java.util.ArrayList;
import java.util.List;




public class ReaderManager {
    private static ReaderManager instance;
    private  List<Reader> readersAccount;
    private static Reader currentReader;

    private ReaderManager() {
        this.readersAccount = new ArrayList<>();
    }
    public static ReaderManager getInstance() {
        if (instance == null) {
            instance = new ReaderManager();
        }
        return instance;
    }

    public String createAccount(String username, String email, String password) {
        ReaderManager manager = ReaderManager.getInstance();
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            System.out.println("Cannot create account with empty credentials");
            return "EMPTY_CREDENTIALS";
        }
        for(Reader reader:manager.getReaders())
        {
            
            if(reader.getUsername().equals(username) || reader.getEmail().equals(email))
            {
                return "ACCOUNT_EXISTS";
            }
        }
        Reader newReader = new Reader(username, email, password);
        readersAccount.add(newReader);
        System.out.println("Account created successfully");
        return "SUCCESS";
    }

    public boolean logIn(String username, String email, String password) {

        // Check each reader's credentials
        for (Reader reader : readersAccount) {
            if ((reader.getUsername().equals(username) || reader.getEmail().equals(email)) &&
                reader.getPassword().equals(password)) {
                currentReader = reader;
                return true; // Successfully logged in
            }
        }
        
        // No matching reader found
        return false;
    }

    public static Reader getCurrentReader() {
        return currentReader;
    }
        

    public  List<Reader> getReaders() {
        return readersAccount;
    }
}
