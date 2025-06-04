package com.example.courseprojectdb.dao;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.List;

public class UsersDAO extends BaseDAO {
    private static final String SELECT_ALL = 
        "SELECT id, full_name, email, role, created_at FROM public.users ORDER BY id";
    
    private static final String INSERT = 
        "CALL public.insert_user(?, ?, ?, ?)";
    
    private static final String UPDATE = 
        "CALL public.update_user(?, ?, ?, ?)";
    
    private static final String DELETE = 
        "CALL public.delete_user(?)";

    private static final String AUTHENTICATE = 
        "SELECT id, role, password FROM public.users WHERE email = ?";

    private static final String UPDATE_PASSWORD =
        "CALL public.update_user_password(?, ?)";

    public List<String[]> getAllUsers() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertUser(String fullName, String email, String password, String role) throws SQLException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return executeUpdate(INSERT, fullName, email, hashedPassword, role);
    }

    public int updateUser(int id, String fullName, String email, String role) throws SQLException {
        return executeUpdate(UPDATE, id, fullName, email, role);
    }

    public int updatePassword(int id, String newPassword) throws SQLException {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return executeUpdate(UPDATE_PASSWORD, id, hashedPassword);
    }

    public int deleteUser(int id) throws SQLException {
        return executeUpdate(DELETE, id);
    }

    public String[] authenticateUser(String email, String password) throws SQLException {
        List<String[]> results = executeQuery(AUTHENTICATE, email);
        
        if (!results.isEmpty()) {
            String[] userData = results.get(0);
            String storedHash = userData[2]; // password is the third column in our query
            
            if (BCrypt.checkpw(password, storedHash)) {
                return new String[]{userData[0], userData[1]}; // return id and role
            }
        }
        return null;
    }
} 