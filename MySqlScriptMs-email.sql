CREATE DATABASE IF NOT EXISTS ms_email;

USE ms_email;

CREATE TABLE IF NOT EXISTS emails (
  id INT AUTO_INCREMENT PRIMARY KEY,
  from_email VARCHAR(255),
  from_name VARCHAR(255),
  reply_to VARCHAR(255),
  to_email VARCHAR(255),
  subject VARCHAR(255),
  body TEXT,
  content_type VARCHAR(50)
);