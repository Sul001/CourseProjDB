package com.example.courseprojectdb.dao;

import java.sql.SQLException;
import java.util.List;

public class SportsDAO extends BaseDAO {
    private static final String SELECT_ALL = 
        "SELECT id, name, description FROM public.sports ORDER BY id";
    
    private static final String INSERT = 
        "INSERT INTO public.sports (name, description) VALUES (?, ?)";
    
    private static final String UPDATE = 
        "UPDATE public.sports SET name = ?, description = ? WHERE id = ?";
    
    private static final String DELETE = 
        "DELETE FROM public.sports WHERE id = ?";

    public List<String[]> getAllSports() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertSport(String name, String description) throws SQLException {
        return executeUpdate(INSERT, name, description);
    }

    public int updateSport(int id, String name, String description) throws SQLException {
        return executeUpdate(UPDATE, name, description, id);
    }

    public int deleteSport(int id) throws SQLException {
        return executeUpdate(DELETE, id);
    }

    public List<String[]> getSportsForComboBox() throws SQLException {
        return executeQuery("SELECT id, name FROM public.sports ORDER BY name");
    }
} 