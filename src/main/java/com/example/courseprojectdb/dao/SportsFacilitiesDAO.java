package com.example.courseprojectdb.dao;

import java.sql.SQLException;
import java.util.List;

public class SportsFacilitiesDAO extends BaseDAO {
    private static final String SELECT_ALL = 
        "SELECT id, name, type, address, capacity, surface_type FROM public.sports_facilities ORDER BY id";
    
    private static final String INSERT = 
        "INSERT INTO public.sports_facilities (name, type, address, capacity, surface_type) VALUES (?, ?, ?, ?, ?)";
    
    private static final String UPDATE = 
        "UPDATE public.sports_facilities SET name = ?, type = ?, address = ?, capacity = ?, surface_type = ? WHERE id = ?";
    
    private static final String DELETE = 
        "DELETE FROM public.sports_facilities WHERE id = ?";

    public List<String[]> getAllFacilities() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertFacility(String name, String type, String address, Integer capacity, String surfaceType) throws SQLException {
        return executeUpdate(INSERT, name, type, address, capacity, surfaceType);
    }

    public int updateFacility(int id, String name, String type, String address, Integer capacity, String surfaceType) throws SQLException {
        return executeUpdate(UPDATE, name, type, address, capacity, surfaceType, id);
    }

    public int deleteFacility(int id) throws SQLException {
        return executeUpdate(DELETE, id);
    }

    public List<String[]> getFacilitiesForComboBox() throws SQLException {
        return executeQuery("SELECT id, name FROM public.sports_facilities ORDER BY name");
    }

    public List<String[]> getFacilityTypes() throws SQLException {
        return executeQuery("SELECT DISTINCT type FROM public.sports_facilities ORDER BY type");
    }
} 