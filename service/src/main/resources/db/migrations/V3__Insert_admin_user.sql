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
    '${ADMIN_EMAIL:admin@ecommerce.com}',
    '${ADMIN_PASSWORD_HASH:$2a$10$temporaryPlaceholderHashValueForDevelopment}',
    '${ADMIN_FIRST_NAME:System}',
    '${ADMIN_LAST_NAME:Admin}',
    'ADMIN',
    true
) ON CONFLICT (email) DO NOTHING;