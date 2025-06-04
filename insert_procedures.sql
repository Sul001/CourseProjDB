-- Function to insert a new user
CREATE OR REPLACE PROCEDURE insert_user(
    p_full_name VARCHAR(100),
    p_email VARCHAR(100),
    p_password VARCHAR(100),
    p_role VARCHAR(20)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO users (full_name, email, password, role)
    VALUES (p_full_name, p_email, p_password, p_role);
END;
$$;

-- Function to update a user
CREATE OR REPLACE PROCEDURE update_user(
    p_id INTEGER,
    p_full_name VARCHAR(100),
    p_email VARCHAR(100),
    p_role VARCHAR(20)
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE users 
    SET full_name = p_full_name,
        email = p_email,
        role = p_role,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_id;
END;
$$;

-- Function to delete a user
CREATE OR REPLACE PROCEDURE delete_user(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM users WHERE id = p_id;
END;
$$;

-- Function to insert a new club
CREATE OR REPLACE PROCEDURE insert_club(
    p_name VARCHAR(100),
    p_founding_year INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO clubs (name, founding_year)
    VALUES (p_name, p_founding_year);
END;
$$;

-- Function to update a club
CREATE OR REPLACE PROCEDURE update_club(
    p_id INTEGER,
    p_name VARCHAR(100),
    p_founding_year INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE clubs 
    SET name = p_name,
        founding_year = p_founding_year,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_id;
END;
$$;

-- Function to delete a club
CREATE OR REPLACE PROCEDURE delete_club(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM clubs WHERE id = p_id;
END;
$$;

-- Function to insert a new sport
CREATE OR REPLACE PROCEDURE insert_sport(
    p_name VARCHAR(100),
    p_description TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO sports (name, description)
    VALUES (p_name, p_description);
END;
$$;

-- Function to update a sport
CREATE OR REPLACE PROCEDURE update_sport(
    p_id INTEGER,
    p_name VARCHAR(100),
    p_description TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE sports 
    SET name = p_name,
        description = p_description,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_id;
END;
$$;

-- Function to delete a sport
CREATE OR REPLACE PROCEDURE delete_sport(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM sports WHERE id = p_id;
END;
$$;

-- Function to insert a new facility
CREATE OR REPLACE PROCEDURE insert_facility(
    p_name VARCHAR(100),
    p_type VARCHAR(20),
    p_address TEXT,
    p_capacity INTEGER,
    p_surface_type VARCHAR(50)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO sports_facilities (name, type, address, capacity, surface_type)
    VALUES (p_name, p_type, p_address, p_capacity, p_surface_type);
END;
$$;

-- Function to update a facility
CREATE OR REPLACE PROCEDURE update_facility(
    p_id INTEGER,
    p_name VARCHAR(100),
    p_type VARCHAR(20),
    p_address TEXT,
    p_capacity INTEGER,
    p_surface_type VARCHAR(50)
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE sports_facilities 
    SET name = p_name,
        type = p_type,
        address = p_address,
        capacity = p_capacity,
        surface_type = p_surface_type,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_id;
END;
$$;

-- Function to delete a facility
CREATE OR REPLACE PROCEDURE delete_facility(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM sports_facilities WHERE id = p_id;
END;
$$;

-- Function to insert a new athlete
CREATE OR REPLACE PROCEDURE insert_athlete(
    p_user_id INTEGER,
    p_club_id INTEGER,
    p_birth_date DATE
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO athletes (user_id, club_id, birth_date)
    VALUES (p_user_id, p_club_id, p_birth_date);
END;
$$;

-- Function to update an athlete
CREATE OR REPLACE PROCEDURE update_athlete(
    p_id INTEGER,
    p_club_id INTEGER,
    p_birth_date DATE
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE athletes 
    SET club_id = p_club_id,
        birth_date = p_birth_date,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_id;
END;
$$;

-- Function to delete an athlete
CREATE OR REPLACE PROCEDURE delete_athlete(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM athletes WHERE id = p_id;
END;
$$;

-- Function to insert a new competition
CREATE OR REPLACE PROCEDURE insert_competition(
    p_name VARCHAR(100),
    p_sport_id INTEGER,
    p_facility_id INTEGER,
    p_organizer_id INTEGER,
    p_start_date DATE,
    p_end_date DATE
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO competitions (name, sport_id, facility_id, organizer_id, start_date, end_date)
    VALUES (p_name, p_sport_id, p_facility_id, p_organizer_id, p_start_date, p_end_date);
END;
$$;

-- Function to update a competition
CREATE OR REPLACE PROCEDURE update_competition(
    p_id INTEGER,
    p_name VARCHAR(100),
    p_sport_id INTEGER,
    p_facility_id INTEGER,
    p_organizer_id INTEGER,
    p_start_date DATE,
    p_end_date DATE
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE competitions 
    SET name = p_name,
        sport_id = p_sport_id,
        facility_id = p_facility_id,
        organizer_id = p_organizer_id,
        start_date = p_start_date,
        end_date = p_end_date,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_id;
END;
$$;

-- Function to delete a competition
CREATE OR REPLACE PROCEDURE delete_competition(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM competitions WHERE id = p_id;
END;
$$;

-- Function to insert a competition result
CREATE OR REPLACE PROCEDURE insert_competition_result(
    p_competition_id INTEGER,
    p_athlete_id INTEGER,
    p_position INTEGER,
    p_award VARCHAR(100)
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO competition_results (competition_id, athlete_id, position, award)
    VALUES (p_competition_id, p_athlete_id, p_position, p_award);
END;
$$;

-- Function to update a competition result
CREATE OR REPLACE PROCEDURE update_competition_result(
    p_id INTEGER,
    p_position INTEGER,
    p_award VARCHAR(100)
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE competition_results 
    SET position = p_position,
        award = p_award,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_id;
END;
$$;

-- Function to delete a competition result
CREATE OR REPLACE PROCEDURE delete_competition_result(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM competition_results WHERE id = p_id;
END;
$$;

-- Create extension for password hashing if not exists
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Create admin user if not exists
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE role = 'admin') THEN
        CALL insert_user('Administrator', 'admin@admin.com', 'admin123', 'admin');
    END IF;
END;
$$; 