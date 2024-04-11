/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginsystem;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author Joshua Souphanthong
 */
/**
 * Class for managing a simple login system.
 */
public class LoginSystem {
    // List of users
    ArrayList<User> users;
    // Delimiter for separating data fields
    private String delimiter = ",";
    // File storing user data
    final String datafile = "userdatabase.txt";

    /**
     * Main method to test the login system.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }  

    /**
     * Encrypts the given password using MD5 hashing algorithm.
     * @param pwd the password to encrypt
     * @return the encrypted password
     * @throws NoSuchAlgorithmException if MD5 algorithm is not available
     */
    public String encryptPwd(String pwd) throws NoSuchAlgorithmException {
        // Create MD5 instance
        MessageDigest md = MessageDigest.getInstance("MD5");
        String encryptedPwd = "";
        // Update MD5 with password bytes
        md.update(pwd.getBytes());
        // Generate hash bytes
        byte byteData[] = md.digest();
        // Convert bytes to hexadecimal string
        for (int i = 0; i < byteData.length; ++i) {
            encryptedPwd += (Integer.toHexString((byteData[i] & 0xFF) | 0x100).substring(1,3));
        }
        return encryptedPwd;
    }

    /**
     * Saves a new user to the database file.
     * @param username the username of the new user
     * @param password the password of the new user
     * @param firstName the first name of the new user
     * @param lastName the last name of the new user
     * @param email the email of the new user
     * @return status code: 1 - username not unique, 2 - weak password,
     *                     3 - contains delimiter, 4 - success, 5 - error, 6 - password too short, 7 - contains space
     */
    public int saveUser(String username, String password, String firstName, String lastName, String email) {
        try {
            // Check for delimiter in input fields
            if (hasDelimiter(username, password, firstName, lastName, email)) {
                return 3;
            }
            // Check for unique username
            if (!isUniqueName(username)) {
                return 1;
            }
            // Check for spaces in input fields
            if (username.contains(" ") || password.contains(" ") || firstName.contains(" ") || lastName.contains(" ") || email.contains(" ")) {
                return 7;
            }
            // Check password strength
            if (!isStrongPwd(password)) {
                return 2;
            }
            // Check password length
            if (password.length() < 8) {
                return 6;
            }
            
            // Generate a random salt
            String salt = "";
            Random random = new Random();
            char r1 = (char)(random.nextInt(26) + 65);
            char r2 = (char)(random.nextInt(26) + 65);
            char r3 = (char)(random.nextInt(26) + 65);
            salt += (r1) + (r2) + (r3);
            
            // If all checks passed, encrypt password with salt and save user to file
            if (isStrongPwd(password) && isUniqueName(username) && !hasDelimiter(username, password, firstName, lastName, email)) {
                PrintWriter writer = new PrintWriter(new FileWriter (datafile,true));
                String encryptedPwd = encryptPwd(password + salt);
                writer.println(username + "," + encryptedPwd + "," + firstName + "," + lastName + "," + email + "," + salt);
                writer.close();
                return 4;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return 5;
    }

    /**
     * Authenticates the user.
     * @param reqPwd the password entered by the user
     * @param reqName the username entered by the user
     * @return true if authentication is successful, false otherwise
     * @throws NoSuchAlgorithmException if MD5 algorithm is not available
     */
    public boolean Authenticate(String reqPwd, String reqName) throws NoSuchAlgorithmException {
        for (User u: users) {
            if (u.getUsername().equals(reqName)) {
                reqPwd += u.getSalt();
                reqPwd = reqPwd.replaceAll("\\s+","");
                reqPwd = encryptPwd(reqPwd);
                if (reqPwd.equals(u.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the username is unique.
     * @param name the username to check
     * @return true if the username is unique, false otherwise
     */
    public boolean isUniqueName(String name) {
        for (int i = 0; i < users.size(); i ++) {
            if (name.equals(users.get(i).getUsername())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any input field contains the delimiter.
     * @param username the username
     * @param password the password
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email
     * @return true if any input field contains the delimiter, false otherwise
     */
    public boolean hasDelimiter(String username, String password, String firstName, String lastName, String email) {
        if (username.contains(delimiter) || password.contains(delimiter) || firstName.contains(delimiter) || lastName.contains(delimiter) || email.contains(delimiter)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the password is strong.
     * @param pwd the password to check
     * @return true if the password is strong, false otherwise
     */
    public boolean isStrongPwd(String pwd) {
        boolean upperCase = false;
        boolean lowerCase = false;
        boolean specialChar = false;
        boolean number = false;
        
        for (int i = 0; i < pwd.length(); i++) {
            if (Character.isUpperCase(pwd.charAt(i))) {
                upperCase = true;
            }
            if (Character.isLowerCase(pwd.charAt(i))) {
                lowerCase = true;
            }
            if (Character.isDigit(pwd.charAt(i))) {
                number = true;
            }
            if (!Character.isDigit(pwd.charAt(i)) && !Character.isLetter(pwd.charAt(i)) && pwd.charAt(i) != ' ' && pwd.charAt(i) != delimiter.charAt(0)) {
                specialChar = true;
            }
        }
        if (upperCase && lowerCase && specialChar && number) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Loads users from the database file.
     */
    public void loadUsers() {
        ArrayList<User> users = new ArrayList<User>();
        String [] currentLine;
        int numOfUser = 0;
        
        try {
            File f = new File("userdatabase.txt");
            Scanner s = new Scanner(f);
            
            while (s.hasNextLine()) {
                numOfUser += 1;
                String line = s.nextLine();
                currentLine = line.split(delimiter);
                User currentUser = new User(currentLine[0], currentLine[1],currentLine[2], currentLine[3], currentLine[4], currentLine[5]);
                users.add(currentUser);
            }
            s.close();
            this.users = users;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Getter for the delimiter.
     * @return the delimiter
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * Setter for the delimiter.
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }
}
