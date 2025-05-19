
-- changeset ZuhairWaheed:1747691908-1
CREATE TABLE users (
    id Int NOT NULL PRIMARY KEY auto_increment,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    role VARCHAR(50) DEFAULT 'USER'
);

CREATE TABLE documents (
    id Int NOT NULL PRIMARY KEY auto_increment,
    document_name VARCHAR(255),
    document_type VARCHAR(100),
    file_path VARCHAR(500),
    uploaded_at DATETIME,
    user_id Int,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
