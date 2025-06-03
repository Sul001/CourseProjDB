package com.example.courseprojectdb.dao;

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

    public List<String[]> getAllUsers() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertUser(String fullName, String email, String password, String role) throws SQLException {
        return executeUpdate(INSERT, fullName, email, password, role);
    }

    public int updateUser(int id, String fullName, String email, String role) throws SQLException {
        return executeUpdate(UPDATE, id, fullName, email, role);
    }

    public int deleteUser(int id) throws SQLException {
        return executeUpdate(DELETE, id);
    }
} 