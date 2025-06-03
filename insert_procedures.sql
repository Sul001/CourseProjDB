CREATE OR REPLACE PROCEDURE public.insert_user(
    p_full_name VARCHAR(100),
    p_email VARCHAR(100),
    p_password VARCHAR(100),
    p_role VARCHAR(20)
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Validate role
    IF p_role NOT IN ('admin', 'organizer', 'trainer', 'athlete') THEN
        RAISE EXCEPTION 'Invalid role. Must be one of: admin, organizer, trainer, athlete';
    END IF;

    INSERT INTO public.users (full_name, email, password, role)
    VALUES (p_full_name, p_email, p_password, p_role);
END;
$$;

-- Update procedure for users with optional parameters
CREATE OR REPLACE PROCEDURE public.update_user(
    p_id INTEGER,
    p_full_name VARCHAR(100) DEFAULT NULL,
    p_email VARCHAR(100) DEFAULT NULL,
    p_password VARCHAR(100) DEFAULT NULL,
    p_role VARCHAR(20) DEFAULT NULL
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_current_full_name VARCHAR(100);
    v_current_email VARCHAR(100);
    v_current_password VARCHAR(100);
    v_current_role VARCHAR(20);
BEGIN
    -- Get current values
    SELECT full_name, email, password, role
    INTO v_current_full_name, v_current_email, v_current_password, v_current_role
    FROM public.users
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'User with ID % not found', p_id;
    END IF;

    -- Use provided values or keep current ones
    p_full_name := COALESCE(p_full_name, v_current_full_name);
    p_email := COALESCE(p_email, v_current_email);
    p_password := COALESCE(p_password, v_current_password);
    p_role := COALESCE(p_role, v_current_role);

    -- Validate role if it's being updated
    IF p_role NOT IN ('admin', 'organizer', 'trainer', 'athlete') THEN
        RAISE EXCEPTION 'Invalid role. Must be one of: admin, organizer, trainer, athlete';
    END IF;

    UPDATE public.users
    SET 
        full_name = p_full_name,
        email = p_email,
        password = p_password,
        role = p_role
    WHERE id = p_id;
END;
$$;

-- Delete procedure for users
CREATE OR REPLACE PROCEDURE public.delete_user(
    p_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM public.users
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'User with ID % not found', p_id;
    END IF;
END;
$$; 