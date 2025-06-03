package com.example.courseprojectdb.dao;

import java.sql.SQLException;
import java.util.List;

public class CompetitionsDAO extends BaseDAO {
    private static final String SELECT_ALL = 
        "SELECT c.id, c.name, s.name as sport_name, f.name as facility_name, " +
        "u.full_name as organizer_name, c.start_date, c.end_date " +
        "FROM public.competitions c " +
        "JOIN public.sports s ON c.sport_id = s.id " +
        "JOIN public.sports_facilities f ON c.facility_id = f.id " +
        "JOIN public.users u ON c.organizer_id = u.id " +
        "ORDER BY c.start_date DESC";

    public List<String[]> getAllCompetitions() throws SQLException {
        return executeQuery(SELECT_ALL);
    }

    public int insertCompetition(String name, int sportId, int facilityId, int organizerId, 
                               String startDate, String endDate) throws SQLException {
        return executeUpdate(
            "CALL public.insert_competition(?, ?, ?, ?, ?, ?)",
            name, sportId, facilityId, organizerId, startDate, endDate
        );
    }

    public int updateCompetition(int id, String name, int sportId, int facilityId, 
                               int organizerId, String startDate, String endDate) throws SQLException {
        return executeUpdate(
            "CALL public.update_competition(?, ?, ?, ?, ?, ?, ?)",
            id, name, sportId, facilityId, organizerId, startDate, endDate
        );
    }

    public int deleteCompetition(int id) throws SQLException {
        return executeUpdate("CALL public.delete_competition(?)", id);
    }

    public List<String[]> getOrganizers() throws SQLException {
        return executeQuery(
            "SELECT id, full_name FROM public.users WHERE role = 'organizer' ORDER BY full_name"
        );
    }
} 