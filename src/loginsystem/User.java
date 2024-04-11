/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;
import java.io.*;
import java.util.Scanner;

/**
 * Represents a user in the login system.
 * @author Joshua Soup
 */
public class User {

    // User attributes
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String salt;
    boolean loggedIn = false;
    private File database = new File("userdatabase.txt");
    
    /**
     * Constructs a new User object with the given attributes.
     * @param username the username of the user
     * @param password the password of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param salt the salt used for password encryption
     */
    public User(String username, String password, String firstName, String lastName, String email, String salt){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
    }
    
    /**
     * Checks if the user is logged in.
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLoggedIn(){
        return loggedIn;
    }
    
    /**
     * Returns the user information.
     * @return a string containing user information
     */
    public String userInfo(){
        String info = "Username: " +  username + " Email: " +  email + " First name: " +  firstName + " Last name: " +  lastName  + " Password: " +  password + " Salt: " + salt ;
        return info;
    }

    /**
     * Getter for the username.
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password.
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the first name.
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name.
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the last name.
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the last name.
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the email.
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the salt.
     * @return the salt used for password encryption
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Setter for the salt.
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Getter for the database file.
     * @return the database file
     */
    public File getDatabase() {
        return database;
    }

    /**
     * Setter for the database file.
     * @param database the database file to set
     */
    public void setDatabase(File database) {
        this.database = database;
    }
}