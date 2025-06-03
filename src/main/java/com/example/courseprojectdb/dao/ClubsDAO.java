package com.example.courseprojectdb.dao;

import java.sql.SQLException;
import java.util.List;

public class ClubsDAO extends BaseDAO {
    private static final String SELECT_ALL = 
        "SELECT id, name, foundation_year FROM public.clubs ORDER BY id";
    
    private static final String INSERT = 
        "INSERT INTO public.clubs (name, foundation_year) VALUES (?, ?)";
    
    private static final String UPDATE = 
        "UPDATE public.clubs SET name = ?, foundation_year = ? WHERE id = ?";
    
    private static final String DELETE = 
        "DELETE FROM public.clubs WHERE id = ?";

    public List<String[]> getAllClubs() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertClub(String name, Integer foundationYear) throws SQLException {
        return executeUpdate(INSERT, name, foundationYear);
    }

    public int updateClub(int id, String name, Integer foundationYear) throws SQLException {
        return executeUpdate(UPDATE, name, foundationYear, id);
    }

    public int deleteClub(int id) throws SQLException {
        return executeUpdate(DELETE, id);
    }

    public List<String[]> getClubsForComboBox() throws SQLException {
        return executeQuery("SELECT id, name FROM public.clubs ORDER BY name");
    }
} 