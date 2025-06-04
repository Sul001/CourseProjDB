package com.example.courseprojectdb.dao;

import java.sql.SQLException;
import java.util.List;

public class CompetitionResultsDAO extends BaseDAO {
    private static final String SELECT_ALL = 
        "SELECT r.id, c.name as competition_name, u.full_name as athlete_name, " +
        "r.position, r.award " +
        "FROM public.competition_results r " +
        "JOIN public.competitions c ON r.competition_id = c.id " +
        "JOIN public.athletes a ON r.athlete_id = a.id " +
        "JOIN public.users u ON a.user_id = u.id " +
        "ORDER BY c.start_date DESC, r.position";
    
    private static final String INSERT = 
        "CALL public.insert_competition_result(?, ?, ?, ?)";
    
    private static final String UPDATE = 
        "CALL public.update_competition_result(?, ?, ?)";
    
    private static final String DELETE = 
        "CALL public.delete_competition_result(?)";

    public List<String[]> getAllResults() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertResult(int competitionId, int athleteId, int position, String award) throws SQLException {
        return executeUpdate(INSERT, competitionId, athleteId, position, award);
    }

    public int updateResult(int id, int position, String award) throws SQLException {
        return executeUpdate(UPDATE, id, position, award);
    }

    public int deleteResult(int id) throws SQLException {
        return executeUpdate(DELETE, id);
    }

    public List<String[]> getAvailableAthletes(int competitionId) throws SQLException {
        String query = 
            "SELECT DISTINCT a.id, u.full_name " +
            "FROM public.athletes a " +
            "JOIN public.users u ON a.user_id = u.id " +
            "WHERE a.id NOT IN (" +
            "   SELECT athlete_id " +
            "   FROM public.competition_results " +
            "   WHERE competition_id = ?" +
            ") " +
            "ORDER BY u.full_name";
        return executeQuery(query, competitionId);
    }
} 