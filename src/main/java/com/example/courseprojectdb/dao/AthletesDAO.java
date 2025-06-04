package com.example.courseprojectdb.dao;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class AthletesDAO extends BaseDAO {
    private static final String SELECT_ALL = 
        "SELECT a.id, u.full_name, c.name as club_name, a.birth_date " +
        "FROM public.athletes a " +
        "JOIN public.users u ON a.user_id = u.id " +
        "JOIN public.clubs c ON a.club_id = c.id " +
        "ORDER BY a.id";
    
    private static final String INSERT = 
        "INSERT INTO public.athletes (user_id, club_id, birth_date) VALUES (?, ?, ?::date)";
    
    private static final String UPDATE = 
        "UPDATE public.athletes SET club_id = ?, birth_date = ?::date WHERE id = ?";
    
    private static final String DELETE = 
        "DELETE FROM public.athletes WHERE id = ?";

    public List<String[]> getAllAthletes() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertAthlete(int userId, int clubId, String birthDate) throws SQLException {
        return executeUpdate(INSERT, userId, clubId, birthDate);
    }

    public int updateAthlete(int id, int clubId, String birthDate) throws SQLException {
        return executeUpdate(UPDATE, clubId, birthDate, id);
    }

    public int deleteAthlete(int id) throws SQLException {
        return executeUpdate(DELETE, id);
    }

    public List<String[]> getAvailableUsers() throws SQLException {
        String query = 
            "SELECT id, full_name FROM public.users " +
            "WHERE role = 'athlete' AND id NOT IN (SELECT user_id FROM public.athletes) " +
            "ORDER BY full_name";
        return executeQuery(query);
    }
} 