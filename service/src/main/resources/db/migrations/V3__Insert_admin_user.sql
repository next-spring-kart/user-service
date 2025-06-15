-- Insert default admin user
-- Password will be set via environment variable or updated manually after deployment
INSERT INTO users (
    email,
    password,
    first_name,
    last_name,
    role,
    is_active
) VALUES (
    '${ADMIN_EMAIL}',
    '${ADMIN_PASSWORD_HASH}',
    '${ADMIN_FIRST_NAME}',
    '${ADMIN_LAST_NAME}',
    'ADMIN',
    true
) ON CONFLICT (email) DO NOTHING;