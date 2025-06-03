module com.example.courseprojectdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    
    exports com.example.courseprojectdb;
    exports com.example.courseprojectdb.dao;
    exports com.example.courseprojectdb.util;
    
    opens com.example.courseprojectdb to javafx.fxml;
}