INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Ada', 'Lovelace', 'ada.lovelace@cis530.example.edu', 'Computer Science', 4.00, 2024
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'ada.lovelace@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Alan', 'Turing', 'alan.turing@cis530.example.edu', 'Computer Science', 3.92, 2023
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'alan.turing@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Katherine', 'Johnson', 'katherine.johnson@cis530.example.edu', 'Mathematics', 3.98, 2022
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'katherine.johnson@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Grace', 'Hopper', 'grace.hopper@cis530.example.edu', 'Computer Science', 3.87, 2021
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'grace.hopper@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'James', 'Maxwell', 'james.maxwell@cis530.example.edu', 'Physics', 3.76, 2024
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'james.maxwell@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Mary', 'Jackson', 'mary.jackson@cis530.example.edu', 'Engineering', 3.91, 2023
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'mary.jackson@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'George', 'Washington Carver', 'george.carver@cis530.example.edu', 'Biology', 3.68, 2022
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'george.carver@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Dorothy', 'Vaughan', 'dorothy.vaughan@cis530.example.edu', 'Mathematics', 3.84, 2021
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'dorothy.vaughan@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Claude', 'Shannon', 'claude.shannon@cis530.example.edu', 'Electrical Engineering', 3.95, 2024
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'claude.shannon@cis530.example.edu');

INSERT INTO students (first_name, last_name, email, major, gpa, enrollment_year)
SELECT 'Hedy', 'Lamarr', 'hedy.lamarr@cis530.example.edu', 'Engineering', 3.73, 2023
WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'hedy.lamarr@cis530.example.edu');