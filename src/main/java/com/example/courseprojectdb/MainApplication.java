package com.example.courseprojectdb;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import com.example.courseprojectdb.dao.*;
import java.sql.SQLException;
import java.util.List;

public class MainApplication extends Application {
    private UsersDAO usersDAO;
    private ClubsDAO clubsDAO;
    private SportsDAO sportsDAO;
    private SportsFacilitiesDAO facilitiesDAO;
    private AthletesDAO athletesDAO;
    private CompetitionsDAO competitionsDAO;
    private CompetitionResultsDAO resultsDAO;

    @Override
    public void start(Stage stage) {
        usersDAO = new UsersDAO();
        clubsDAO = new ClubsDAO();
        sportsDAO = new SportsDAO();
        facilitiesDAO = new SportsFacilitiesDAO();
        athletesDAO = new AthletesDAO();
        competitionsDAO = new CompetitionsDAO();
        resultsDAO = new CompetitionResultsDAO();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
            createUsersTab(),
            createClubsTab(),
            createSportsTab(),
            createFacilitiesTab(),
            createAthletesTab(),
            createCompetitionsTab(),
            createResultsTab()
        );

        Scene scene = new Scene(new VBox(tabPane), 1200, 800);
        stage.setTitle("Sports Database Management");
        stage.setScene(scene);
        stage.show();
    }

    private Tab createUsersTab() {
        Tab tab = new Tab("Users");
        tab.setClosable(false);

        TableView<String[]> tableView = new TableView<>();
        
        // Add columns
        String[] columns = {"ID", "Full Name", "Email", "Role", "Created At"};
        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(data -> {
                String[] row = data.getValue();
                return row != null && row.length > columnIndex ? 
                    new SimpleStringProperty(row[columnIndex]) : 
                    new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }

        // Add controls for inserting/updating users
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("admin", "organizer", "trainer", "athlete");

        Button addButton = new Button("Add User");
        addButton.setOnAction(e -> {
            try {
                usersDAO.insertUser(
                    fullNameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    roleComboBox.getValue()
                );
                refreshTable(tableView);
                clearControls(fullNameField, emailField, passwordField, roleComboBox);
            } catch (Exception ex) {
                showError("Error adding user", ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> {
            String[] selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                try {
                    usersDAO.updateUser(
                        Integer.parseInt(selectedUser[0]),
                        fullNameField.getText(),
                        emailField.getText(),
                        roleComboBox.getValue()
                    );
                    refreshTable(tableView);
                    clearControls(fullNameField, emailField, passwordField, roleComboBox);
                } catch (Exception ex) {
                    showError("Error updating user", ex.getMessage());
                }
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            String[] selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                try {
                    usersDAO.deleteUser(Integer.parseInt(selectedUser[0]));
                    refreshTable(tableView);
                } catch (Exception ex) {
                    showError("Error deleting user", ex.getMessage());
                }
            }
        });

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fullNameField.setText(newSelection[1]);
                emailField.setText(newSelection[2]);
                roleComboBox.setValue(newSelection[3]);
            }
        });

        VBox controls = new VBox(10,
            fullNameField, emailField, passwordField, roleComboBox,
            new HBox(10, addButton, updateButton, deleteButton)
        );
        controls.setStyle("-fx-padding: 10;");

        VBox content = new VBox(10, controls, tableView);
        tab.setContent(content);

        // Initial table load
        refreshTable(tableView);

        return tab;
    }

    private Tab createClubsTab() {
        Tab tab = new Tab("Clubs");
        tab.setClosable(false);

        TableView<String[]> tableView = new TableView<>();
        
        // Add columns
        String[] columns = {"ID", "Name", "Foundation Year"};
        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(data -> {
                String[] row = data.getValue();
                return row != null && row.length > columnIndex ? 
                    new SimpleStringProperty(row[columnIndex]) : 
                    new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }

        // Add controls for inserting/updating clubs
        TextField nameField = new TextField();
        nameField.setPromptText("Club Name");
        TextField yearField = new TextField();
        yearField.setPromptText("Foundation Year");

        Button addButton = new Button("Add Club");
        addButton.setOnAction(e -> {
            try {
                Integer year = yearField.getText().isEmpty() ? null : Integer.parseInt(yearField.getText());
                clubsDAO.insertClub(
                    nameField.getText(),
                    year
                );
                refreshClubsTable(tableView);
                clearControls(nameField, yearField);
            } catch (Exception ex) {
                showError("Error adding club", ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> {
            String[] selectedClub = tableView.getSelectionModel().getSelectedItem();
            if (selectedClub != null) {
                try {
                    Integer year = yearField.getText().isEmpty() ? null : Integer.parseInt(yearField.getText());
                    clubsDAO.updateClub(
                        Integer.parseInt(selectedClub[0]),
                        nameField.getText(),
                        year
                    );
                    refreshClubsTable(tableView);
                    clearControls(nameField, yearField);
                } catch (Exception ex) {
                    showError("Error updating club", ex.getMessage());
                }
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            String[] selectedClub = tableView.getSelectionModel().getSelectedItem();
            if (selectedClub != null) {
                try {
                    clubsDAO.deleteClub(Integer.parseInt(selectedClub[0]));
                    refreshClubsTable(tableView);
                } catch (Exception ex) {
                    showError("Error deleting club", ex.getMessage());
                }
            }
        });

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection[1]);
                yearField.setText(newSelection[2]);
            }
        });

        VBox controls = new VBox(10,
            nameField, yearField,
            new HBox(10, addButton, updateButton, deleteButton)
        );
        controls.setStyle("-fx-padding: 10;");

        VBox content = new VBox(10, controls, tableView);
        tab.setContent(content);

        // Initial table load
        refreshClubsTable(tableView);

        return tab;
    }

    private Tab createSportsTab() {
        Tab tab = new Tab("Sports");
        tab.setClosable(false);

        TableView<String[]> tableView = new TableView<>();
        
        // Add columns
        String[] columns = {"ID", "Name", "Description"};
        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(data -> {
                String[] row = data.getValue();
                return row != null && row.length > columnIndex ? 
                    new SimpleStringProperty(row[columnIndex]) : 
                    new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }

        // Add controls for inserting/updating sports
        TextField nameField = new TextField();
        nameField.setPromptText("Sport Name");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        descriptionArea.setPrefRowCount(3);

        Button addButton = new Button("Add Sport");
        addButton.setOnAction(e -> {
            try {
                sportsDAO.insertSport(
                    nameField.getText(),
                    descriptionArea.getText()
                );
                refreshSportsTable(tableView);
                clearControls(nameField, descriptionArea);
            } catch (Exception ex) {
                showError("Error adding sport", ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> {
            String[] selectedSport = tableView.getSelectionModel().getSelectedItem();
            if (selectedSport != null) {
                try {
                    sportsDAO.updateSport(
                        Integer.parseInt(selectedSport[0]),
                        nameField.getText(),
                        descriptionArea.getText()
                    );
                    refreshSportsTable(tableView);
                    clearControls(nameField, descriptionArea);
                } catch (Exception ex) {
                    showError("Error updating sport", ex.getMessage());
                }
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            String[] selectedSport = tableView.getSelectionModel().getSelectedItem();
            if (selectedSport != null) {
                try {
                    sportsDAO.deleteSport(Integer.parseInt(selectedSport[0]));
                    refreshSportsTable(tableView);
                } catch (Exception ex) {
                    showError("Error deleting sport", ex.getMessage());
                }
            }
        });

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection[1]);
                descriptionArea.setText(newSelection[2]);
            }
        });

        VBox controls = new VBox(10,
            nameField, descriptionArea,
            new HBox(10, addButton, updateButton, deleteButton)
        );
        controls.setStyle("-fx-padding: 10;");

        VBox content = new VBox(10, controls, tableView);
        tab.setContent(content);

        // Initial table load
        refreshSportsTable(tableView);

        return tab;
    }

    private Tab createFacilitiesTab() {
        Tab tab = new Tab("Sports Facilities");
        tab.setClosable(false);

        TableView<String[]> tableView = new TableView<>();
        
        // Add columns
        String[] columns = {"ID", "Name", "Type", "Address", "Capacity", "Surface Type"};
        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(data -> {
                String[] row = data.getValue();
                return row != null && row.length > columnIndex ? 
                    new SimpleStringProperty(row[columnIndex]) : 
                    new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }

        // Add controls for inserting/updating facilities
        TextField nameField = new TextField();
        nameField.setPromptText("Facility Name");
        
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("stadium", "gym", "court", "arena", "pool");
        typeComboBox.setPromptText("Facility Type");
        
        TextArea addressArea = new TextArea();
        addressArea.setPromptText("Address");
        addressArea.setPrefRowCount(2);
        
        TextField capacityField = new TextField();
        capacityField.setPromptText("Capacity (for stadiums)");
        
        TextField surfaceField = new TextField();
        surfaceField.setPromptText("Surface Type (for courts)");

        // Type change listener to handle conditional fields
        typeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                capacityField.setDisable(!newVal.equals("stadium"));
                surfaceField.setDisable(!newVal.equals("court"));
            }
        });

        Button addButton = new Button("Add Facility");
        addButton.setOnAction(e -> {
            try {
                Integer capacity = null;
                if (typeComboBox.getValue().equals("stadium") && !capacityField.getText().isEmpty()) {
                    capacity = Integer.parseInt(capacityField.getText());
                }
                
                String surfaceType = null;
                if (typeComboBox.getValue().equals("court") && !surfaceField.getText().isEmpty()) {
                    surfaceType = surfaceField.getText();
                }

                facilitiesDAO.insertFacility(
                    nameField.getText(),
                    typeComboBox.getValue(),
                    addressArea.getText(),
                    capacity,
                    surfaceType
                );
                refreshFacilitiesTable(tableView);
                clearControls(nameField, typeComboBox, addressArea, capacityField, surfaceField);
            } catch (Exception ex) {
                showError("Error adding facility", ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> {
            String[] selectedFacility = tableView.getSelectionModel().getSelectedItem();
            if (selectedFacility != null) {
                try {
                    Integer capacity = null;
                    if (typeComboBox.getValue().equals("stadium") && !capacityField.getText().isEmpty()) {
                        capacity = Integer.parseInt(capacityField.getText());
                    }
                    
                    String surfaceType = null;
                    if (typeComboBox.getValue().equals("court") && !surfaceField.getText().isEmpty()) {
                        surfaceType = surfaceField.getText();
                    }

                    facilitiesDAO.updateFacility(
                        Integer.parseInt(selectedFacility[0]),
                        nameField.getText(),
                        typeComboBox.getValue(),
                        addressArea.getText(),
                        capacity,
                        surfaceType
                    );
                    refreshFacilitiesTable(tableView);
                    clearControls(nameField, typeComboBox, addressArea, capacityField, surfaceField);
                } catch (Exception ex) {
                    showError("Error updating facility", ex.getMessage());
                }
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            String[] selectedFacility = tableView.getSelectionModel().getSelectedItem();
            if (selectedFacility != null) {
                try {
                    facilitiesDAO.deleteFacility(Integer.parseInt(selectedFacility[0]));
                    refreshFacilitiesTable(tableView);
                } catch (Exception ex) {
                    showError("Error deleting facility", ex.getMessage());
                }
            }
        });

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection[1]);
                typeComboBox.setValue(newSelection[2]);
                addressArea.setText(newSelection[3]);
                capacityField.setText(newSelection[4]);
                surfaceField.setText(newSelection[5]);
            }
        });

        VBox controls = new VBox(10,
            nameField, typeComboBox, addressArea,
            new HBox(10, capacityField, surfaceField),
            new HBox(10, addButton, updateButton, deleteButton)
        );
        controls.setStyle("-fx-padding: 10;");

        VBox content = new VBox(10, controls, tableView);
        tab.setContent(content);

        // Initial table load
        refreshFacilitiesTable(tableView);

        return tab;
    }

    private Tab createAthletesTab() {
        Tab tab = new Tab("Athletes");
        tab.setClosable(false);

        TableView<String[]> tableView = new TableView<>();
        
        // Add columns
        String[] columns = {"ID", "Full Name", "Club", "Birth Date"};
        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(data -> {
                String[] row = data.getValue();
                return row != null && row.length > columnIndex ? 
                    new SimpleStringProperty(row[columnIndex]) : 
                    new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }

        // Add controls for inserting/updating athletes
        ComboBox<String> userComboBox = new ComboBox<>();
        userComboBox.setPromptText("Select Athlete User");
        
        ComboBox<String> clubComboBox = new ComboBox<>();
        clubComboBox.setPromptText("Select Club");
        
        DatePicker birthDatePicker = new DatePicker();
        birthDatePicker.setPromptText("Birth Date");

        // Load combo box data
        try {
            List<String[]> availableUsers = athletesDAO.getAvailableUsers();
            for (String[] user : availableUsers) {
                userComboBox.getItems().add(user[0] + " - " + user[1]);
            }

            List<String[]> clubs = clubsDAO.getClubsForComboBox();
            for (String[] club : clubs) {
                clubComboBox.getItems().add(club[0] + " - " + club[1]);
            }
        } catch (SQLException e) {
            showError("Error loading data", e.getMessage());
        }

        Button addButton = new Button("Add Athlete");
        addButton.setOnAction(e -> {
            try {
                if (userComboBox.getValue() == null || clubComboBox.getValue() == null || birthDatePicker.getValue() == null) {
                    showError("Validation Error", "Please fill in all fields");
                    return;
                }

                int userId = Integer.parseInt(userComboBox.getValue().split(" - ")[0]);
                int clubId = Integer.parseInt(clubComboBox.getValue().split(" - ")[0]);
                String birthDate = birthDatePicker.getValue().toString();

                athletesDAO.insertAthlete(userId, clubId, birthDate);
                refreshAthletesTable(tableView);
                clearControls(userComboBox, clubComboBox);
                birthDatePicker.setValue(null);
            } catch (Exception ex) {
                showError("Error adding athlete", ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> {
            String[] selectedAthlete = tableView.getSelectionModel().getSelectedItem();
            if (selectedAthlete != null) {
                try {
                    if (clubComboBox.getValue() == null || birthDatePicker.getValue() == null) {
                        showError("Validation Error", "Please fill in all fields");
                        return;
                    }

                    int clubId = Integer.parseInt(clubComboBox.getValue().split(" - ")[0]);
                    String birthDate = birthDatePicker.getValue().toString();

                    athletesDAO.updateAthlete(
                        Integer.parseInt(selectedAthlete[0]),
                        clubId,
                        birthDate
                    );
                    refreshAthletesTable(tableView);
                    clearControls(clubComboBox);
                    birthDatePicker.setValue(null);
                } catch (Exception ex) {
                    showError("Error updating athlete", ex.getMessage());
                }
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            String[] selectedAthlete = tableView.getSelectionModel().getSelectedItem();
            if (selectedAthlete != null) {
                try {
                    athletesDAO.deleteAthlete(Integer.parseInt(selectedAthlete[0]));
                    refreshAthletesTable(tableView);
                } catch (Exception ex) {
                    showError("Error deleting athlete", ex.getMessage());
                }
            }
        });

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Only update club and birth date for existing athletes
                clubComboBox.getItems().stream()
                    .filter(item -> item.endsWith(newSelection[2]))
                    .findFirst()
                    .ifPresent(clubComboBox::setValue);
                
                try {
                    birthDatePicker.setValue(java.time.LocalDate.parse(newSelection[3]));
                } catch (Exception e) {
                    birthDatePicker.setValue(null);
                }
            }
        });

        VBox controls = new VBox(10,
            userComboBox, clubComboBox, birthDatePicker,
            new HBox(10, addButton, updateButton, deleteButton)
        );
        controls.setStyle("-fx-padding: 10;");

        VBox content = new VBox(10, controls, tableView);
        tab.setContent(content);

        // Initial table load
        refreshAthletesTable(tableView);

        return tab;
    }

    private Tab createCompetitionsTab() {
        Tab tab = new Tab("Competitions");
        tab.setClosable(false);

        TableView<String[]> tableView = new TableView<>();
        
        // Add columns
        String[] columns = {"ID", "Name", "Sport", "Facility", "Organizer", "Start Date", "End Date"};
        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(data -> {
                String[] row = data.getValue();
                return row != null && row.length > columnIndex ? 
                    new SimpleStringProperty(row[columnIndex]) : 
                    new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }

        // Add controls for inserting/updating competitions
        TextField nameField = new TextField();
        nameField.setPromptText("Competition Name");
        
        ComboBox<String> sportComboBox = new ComboBox<>();
        sportComboBox.setPromptText("Select Sport");
        
        ComboBox<String> facilityComboBox = new ComboBox<>();
        facilityComboBox.setPromptText("Select Facility");
        
        ComboBox<String> organizerComboBox = new ComboBox<>();
        organizerComboBox.setPromptText("Select Organizer");
        
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");
        
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");

        // Load combo box data
        try {
            List<String[]> sports = sportsDAO.getSportsForComboBox();
            for (String[] sport : sports) {
                sportComboBox.getItems().add(sport[0] + " - " + sport[1]);
            }

            List<String[]> facilities = facilitiesDAO.getFacilitiesForComboBox();
            for (String[] facility : facilities) {
                facilityComboBox.getItems().add(facility[0] + " - " + facility[1]);
            }

            List<String[]> organizers = competitionsDAO.getOrganizers();
            for (String[] organizer : organizers) {
                organizerComboBox.getItems().add(organizer[0] + " - " + organizer[1]);
            }
        } catch (SQLException e) {
            showError("Error loading data", e.getMessage());
        }

        Button addButton = new Button("Add Competition");
        addButton.setOnAction(e -> {
            try {
                if (nameField.getText().isEmpty() || sportComboBox.getValue() == null || 
                    facilityComboBox.getValue() == null || organizerComboBox.getValue() == null ||
                    startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                    showError("Validation Error", "Please fill in all fields");
                    return;
                }

                int sportId = Integer.parseInt(sportComboBox.getValue().split(" - ")[0]);
                int facilityId = Integer.parseInt(facilityComboBox.getValue().split(" - ")[0]);
                int organizerId = Integer.parseInt(organizerComboBox.getValue().split(" - ")[0]);

                competitionsDAO.insertCompetition(
                    nameField.getText(),
                    sportId,
                    facilityId,
                    organizerId,
                    startDatePicker.getValue().toString(),
                    endDatePicker.getValue().toString()
                );
                refreshCompetitionsTable(tableView);
                clearControls(nameField, sportComboBox, facilityComboBox, organizerComboBox);
                startDatePicker.setValue(null);
                endDatePicker.setValue(null);
            } catch (Exception ex) {
                showError("Error adding competition", ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> {
            String[] selectedCompetition = tableView.getSelectionModel().getSelectedItem();
            if (selectedCompetition != null) {
                try {
                    if (nameField.getText().isEmpty() || sportComboBox.getValue() == null || 
                        facilityComboBox.getValue() == null || organizerComboBox.getValue() == null ||
                        startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                        showError("Validation Error", "Please fill in all fields");
                        return;
                    }

                    int sportId = Integer.parseInt(sportComboBox.getValue().split(" - ")[0]);
                    int facilityId = Integer.parseInt(facilityComboBox.getValue().split(" - ")[0]);
                    int organizerId = Integer.parseInt(organizerComboBox.getValue().split(" - ")[0]);

                    competitionsDAO.updateCompetition(
                        Integer.parseInt(selectedCompetition[0]),
                        nameField.getText(),
                        sportId,
                        facilityId,
                        organizerId,
                        startDatePicker.getValue().toString(),
                        endDatePicker.getValue().toString()
                    );
                    refreshCompetitionsTable(tableView);
                    clearControls(nameField, sportComboBox, facilityComboBox, organizerComboBox);
                    startDatePicker.setValue(null);
                    endDatePicker.setValue(null);
                } catch (Exception ex) {
                    showError("Error updating competition", ex.getMessage());
                }
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            String[] selectedCompetition = tableView.getSelectionModel().getSelectedItem();
            if (selectedCompetition != null) {
                try {
                    competitionsDAO.deleteCompetition(Integer.parseInt(selectedCompetition[0]));
                    refreshCompetitionsTable(tableView);
                } catch (Exception ex) {
                    showError("Error deleting competition", ex.getMessage());
                }
            }
        });

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection[1]);
                
                sportComboBox.getItems().stream()
                    .filter(item -> item.endsWith(newSelection[2]))
                    .findFirst()
                    .ifPresent(sportComboBox::setValue);
                
                facilityComboBox.getItems().stream()
                    .filter(item -> item.endsWith(newSelection[3]))
                    .findFirst()
                    .ifPresent(facilityComboBox::setValue);
                
                organizerComboBox.getItems().stream()
                    .filter(item -> item.endsWith(newSelection[4]))
                    .findFirst()
                    .ifPresent(organizerComboBox::setValue);
                
                try {
                    startDatePicker.setValue(java.time.LocalDate.parse(newSelection[5]));
                    endDatePicker.setValue(java.time.LocalDate.parse(newSelection[6]));
                } catch (Exception e) {
                    startDatePicker.setValue(null);
                    endDatePicker.setValue(null);
                }
            }
        });

        VBox controls = new VBox(10,
            nameField,
            new HBox(10, sportComboBox, facilityComboBox),
            organizerComboBox,
            new HBox(10, startDatePicker, endDatePicker),
            new HBox(10, addButton, updateButton, deleteButton)
        );
        controls.setStyle("-fx-padding: 10;");

        VBox content = new VBox(10, controls, tableView);
        tab.setContent(content);

        // Initial table load
        refreshCompetitionsTable(tableView);

        return tab;
    }

    private Tab createResultsTab() {
        Tab tab = new Tab("Competition Results");
        tab.setClosable(false);

        TableView<String[]> tableView = new TableView<>();
        
        // Add columns
        String[] columns = {"ID", "Competition", "Athlete", "Position", "Award"};
        for (int i = 0; i < columns.length; i++) {
            final int columnIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(data -> {
                String[] row = data.getValue();
                return row != null && row.length > columnIndex ? 
                    new SimpleStringProperty(row[columnIndex]) : 
                    new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }

        // Add controls for inserting/updating results
        ComboBox<String> competitionComboBox = new ComboBox<>();
        competitionComboBox.setPromptText("Select Competition");
        
        ComboBox<String> athleteComboBox = new ComboBox<>();
        athleteComboBox.setPromptText("Select Athlete");
        
        TextField positionField = new TextField();
        positionField.setPromptText("Position");
        
        TextField awardField = new TextField();
        awardField.setPromptText("Award");

        // Load competition combo box data
        try {
            List<String[]> competitions = competitionsDAO.getAllCompetitions();
            for (String[] competition : competitions) {
                competitionComboBox.getItems().add(competition[0] + " - " + competition[1]);
            }
        } catch (SQLException e) {
            showError("Error loading competitions", e.getMessage());
        }

        // Update available athletes when competition is selected
        competitionComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    int competitionId = Integer.parseInt(newVal.split(" - ")[0]);
                    athleteComboBox.getItems().clear();
                    List<String[]> athletes = resultsDAO.getAvailableAthletes(competitionId);
                    for (String[] athlete : athletes) {
                        athleteComboBox.getItems().add(athlete[0] + " - " + athlete[1]);
                    }
                } catch (SQLException e) {
                    showError("Error loading athletes", e.getMessage());
                }
            }
        });

        Button addButton = new Button("Add Result");
        addButton.setOnAction(e -> {
            try {
                if (competitionComboBox.getValue() == null || athleteComboBox.getValue() == null || 
                    positionField.getText().isEmpty() || awardField.getText().isEmpty()) {
                    showError("Validation Error", "Please fill in all fields");
                    return;
                }

                int competitionId = Integer.parseInt(competitionComboBox.getValue().split(" - ")[0]);
                int athleteId = Integer.parseInt(athleteComboBox.getValue().split(" - ")[0]);
                int position = Integer.parseInt(positionField.getText());

                resultsDAO.insertResult(
                    competitionId,
                    athleteId,
                    position,
                    awardField.getText()
                );
                refreshResultsTable(tableView);
                clearControls(athleteComboBox, positionField, awardField);
                
                // Refresh available athletes
                int selectedCompetitionId = Integer.parseInt(competitionComboBox.getValue().split(" - ")[0]);
                athleteComboBox.getItems().clear();
                List<String[]> athletes = resultsDAO.getAvailableAthletes(selectedCompetitionId);
                for (String[] athlete : athletes) {
                    athleteComboBox.getItems().add(athlete[0] + " - " + athlete[1]);
                }
            } catch (Exception ex) {
                showError("Error adding result", ex.getMessage());
            }
        });

        Button updateButton = new Button("Update Selected");
        updateButton.setOnAction(e -> {
            String[] selectedResult = tableView.getSelectionModel().getSelectedItem();
            if (selectedResult != null) {
                try {
                    if (positionField.getText().isEmpty() || awardField.getText().isEmpty()) {
                        showError("Validation Error", "Please fill in all fields");
                        return;
                    }

                    int position = Integer.parseInt(positionField.getText());

                    resultsDAO.updateResult(
                        Integer.parseInt(selectedResult[0]),
                        position,
                        awardField.getText()
                    );
                    refreshResultsTable(tableView);
                    clearControls(competitionComboBox, athleteComboBox, positionField, awardField);
                } catch (Exception ex) {
                    showError("Error updating result", ex.getMessage());
                }
            }
        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            String[] selectedResult = tableView.getSelectionModel().getSelectedItem();
            if (selectedResult != null) {
                try {
                    resultsDAO.deleteResult(Integer.parseInt(selectedResult[0]));
                    refreshResultsTable(tableView);
                } catch (Exception ex) {
                    showError("Error deleting result", ex.getMessage());
                }
            }
        });

        // Selection listener
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                positionField.setText(newSelection[3]);
                awardField.setText(newSelection[4]);
            }
        });

        VBox controls = new VBox(10,
            competitionComboBox, athleteComboBox,
            new HBox(10, positionField, awardField),
            new HBox(10, addButton, updateButton, deleteButton)
        );
        controls.setStyle("-fx-padding: 10;");

        VBox content = new VBox(10, controls, tableView);
        tab.setContent(content);

        // Initial table load
        refreshResultsTable(tableView);

        return tab;
    }

    private void refreshTable(TableView<String[]> tableView) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(usersDAO.getAllUsers());
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }

    private void refreshClubsTable(TableView<String[]> tableView) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(clubsDAO.getAllClubs());
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }

    private void refreshSportsTable(TableView<String[]> tableView) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(sportsDAO.getAllSports());
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }

    private void refreshFacilitiesTable(TableView<String[]> tableView) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(facilitiesDAO.getAllFacilities());
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }

    private void refreshAthletesTable(TableView<String[]> tableView) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(athletesDAO.getAllAthletes());
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }

    private void refreshCompetitionsTable(TableView<String[]> tableView) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(competitionsDAO.getAllCompetitions());
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }

    private void refreshResultsTable(TableView<String[]> tableView) {
        try {
            tableView.getItems().clear();
            tableView.getItems().addAll(resultsDAO.getAllResults());
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }

    private void clearControls(Control... controls) {
        for (Control control : controls) {
            if (control instanceof TextField) {
                ((TextField) control).clear();
            } else if (control instanceof PasswordField) {
                ((PasswordField) control).clear();
            } else if (control instanceof ComboBox<?>) {
                ((ComboBox<?>) control).setValue(null);
            } else if (control instanceof TextArea) {
                ((TextArea) control).clear();
            }
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
} 