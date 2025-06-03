package com.example.courseprojectdb.dao;

import com.example.courseprojectdb.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    protected List<String[]> executeQuery(String query, Object... params) throws SQLException {
        List<String[]> results = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    row[i-1] = value != null ? value.toString() : "";
                }
                results.add(row);
            }
        }
        return results;
    }

    protected int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        }
    }

    protected List<String[]> getComboBoxData(String query, Object... params) throws SQLException {
        return executeQuery(query, params);
    }
} 