-- changeset ZuhairWaheed:1747691908-1
-- Insert a user
INSERT INTO users (email, password, full_name, role)
VALUES ('john.doe@example.com', 'hashed_password_here', 'John Doe', 'USER');

-- Insert a document
INSERT INTO documents (document_name, document_type, file_path, uploaded_at, user_id)
VALUES ('passport.pdf', 'PDF', '/uploads/passport.pdf', NOW(), 1);
