CREATE DATABASE IF NOT EXISTS professional_practices;
USE professional_practices;

CREATE TABLE UserAccount (
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT','TEACHER','COORDINATOR', 'EVALUATOR') NOT NULL
);

INSERT INTO UserAccount (username, password, role) VALUES
('camila.soto', '$2a$10$Pwyws4x/En1kTf5Nyn5B8uYytGh8crNUqbHYDhKOnYXx3olKecPO6', 'STUDENT'),
('diego.martinez', '$2a$10$Me9csHlVAGAims14UFDnv.2KRgTzm6UABIg7xwBtWYm4j9XiZG7Rq', 'TEACHER'),
('valentina.coordinadora', '$2a$10$xxVIErvl.P2zqSJHP4DqYOrEhs40L/0dNKQYvwjwkT6kojkyImZrO', 'COORDINATOR'),
('leonardo.eval', '$2a$10$3xQ2OHt8oaFYXOuGC3LFVeggqq6OkXqBV7sk.zvJgAzCf42Z9JULW', 'EVALUATOR');

INSERT INTO UserAccount (username, password, role) VALUES
('mariana.lopez', '$2a$10$iIp70172T.xpVBCBud5BVePXnwSz8I0UMqbPKtRcgQgP7Hg5o9Vf.', 'STUDENT'),
('santiago.gomez', '$2a$10$uyi90wPZmLE24hzfja/lyOlQumYnwLZ5OAS8LMW1siWX5hApdmyfq', 'STUDENT'),
('isabela.ramirez', '$2a$10$OCmyoyAnaX89Z.77xxuYDeOg1xCWRlwai55C1DUYRw9utKGWRz9t2', 'STUDENT'),
('juan.perez', '$2a$10$s8VRB9wZFcw0ZxpSH9bemuyTm2lMeyx1pbMA51IjCfoQNbj21dkjy', 'TEACHER'),
('alejandro.vargas', '$2a$10$1/zOi7moCqUBdKZdUP.XDuMy3Jza38gvX6gIG/UOja5.r/VkkZgFi', 'EVALUATOR');

CREATE TABLE Student (
    idStudent INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(45) NOT NULL,
    lastNameFather VARCHAR(45) NOT NULL,
    lastNameMother VARCHAR(45),
    enrollment VARCHAR(45) NOT NULL,
    email VARCHAR(45),
    phone VARCHAR(10),
    credits INT,
    semester VARCHAR(45),
    isAssignedToProject TINYINT(1),
    projectSelection VARCHAR(100),
    grade DECIMAL(3,2),
    idUser INT NOT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES UserAccount(idUser)
);

INSERT INTO Student (
    firstName, lastNameFather, lastNameMother, enrollment, email, phone, credits, semester, 
    isAssignedToProject, projectSelection, grade, idUser
) VALUES (
    'Camila', 'Soto', 'García', 'S20000001', 'zS20000001@estudiantes.uv.mx', '5512345678', 330, '10',
    0, NULL, NULL, 1
);

INSERT INTO Student (
    firstName, lastNameFather, lastNameMother, enrollment, email,
    phone, credits, semester, isAssignedToProject, projectSelection,
    grade, idUser
) VALUES
('Mariana', 'López', 'Fernández', 'S20050202', 'zS20050202@estudiantes.uv.mx',
 '2281234567', 340, '10', 0, NULL,
 NULL, 5),

('Santiago', 'Gómez', 'Martínez', 'S19038073', 'zS19038073@estudiantes.uv.mx',
 '2299876543', 351, '12', 0, NULL,
 NULL, 6),

('Isabela', 'Ramírez', 'Domínguez', 'S20015624', 'zS20015624@estudiantes.uv.mx',
 '2287654321', 345, '10', 0, NULL,
 NULL, 7);

CREATE TABLE Subject (
    idSubject INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    credits INT NOT NULL
);

INSERT INTO Subject (name, credits) VALUES
('Prácticas Profesionales', 14);

CREATE TABLE Term (
    idTerm INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    startDate DATE,
    endDate DATE
);

INSERT INTO Term (name, startDate, endDate) VALUES
('Febrero 2025 - Julio 2025', '2025-02-01', '2025-07-31'),
('Agosto 2025 - Enero 2026', '2025-08-01', '2026-01-31');

CREATE TABLE SubjectGroup (
    idSubjectGroup INT AUTO_INCREMENT PRIMARY KEY,
    idTerm INT,
    idSubject INT,
    schedule VARCHAR(100),
    FOREIGN KEY (idTerm) REFERENCES Term(idTerm),
    FOREIGN KEY (idSubject) REFERENCES Subject(idSubject)
);

INSERT INTO SubjectGroup (idTerm, idSubject, schedule) VALUES
(1, 1, 'Lunes y Miércoles 09:00 - 11:00'),
(1, 1, 'Martes y Jueves 14:00 - 16:00');

CREATE TABLE StudentInGroup (
    idStudent INT NOT NULL,
    idSubjectGroup INT NOT NULL,
    PRIMARY KEY (idStudent, idSubjectGroup),
    FOREIGN KEY (idStudent) REFERENCES Student(idStudent),
    FOREIGN KEY (idSubjectGroup) REFERENCES SubjectGroup(idSubjectGroup)
);

INSERT INTO StudentInGroup (idStudent, idSubjectGroup) VALUES
(1, 1);

INSERT INTO StudentInGroup (idStudent, idSubjectGroup) VALUES
(2, 1),
(3, 2),
(4, 2);

CREATE TABLE Academic (
    idAcademic INT AUTO_INCREMENT PRIMARY KEY,
    idSubjectGroup INT,
    firstName VARCHAR(50) NOT NULL,
    lastNameFather VARCHAR(50) NOT NULL,
    lastNameMother VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    status TINYINT(1),
    idUser INT NOT NULL UNIQUE,
    FOREIGN KEY (idSubjectGroup) REFERENCES SubjectGroup(idSubjectGroup),
    FOREIGN KEY (idUser) REFERENCES UserAccount(idUser)
);

INSERT INTO Academic (idSubjectGroup, firstName, lastNameFather, lastNameMother, email, status, idUser) VALUES
(1, 'Diego', 'Martínez', 'López', 'diego.martinez@uv.mx', 1, 2);

INSERT INTO Academic (idSubjectGroup, firstName, lastNameFather, lastNameMother, email, status, idUser)
VALUES (1, 'Juan', 'Pérez', 'Ramírez', 'juan.perez@uv.mx', 1, 8);

CREATE TABLE TeachingAssignment (
    idSubjectGroup INT NOT NULL,
    idAcademic INT NOT NULL,
    PRIMARY KEY (idSubjectGroup, idAcademic),
    FOREIGN KEY (idSubjectGroup) REFERENCES SubjectGroup(idSubjectGroup),
    FOREIGN KEY (idAcademic) REFERENCES Academic(idAcademic)
);

INSERT INTO TeachingAssignment (idSubjectGroup, idAcademic) VALUES
(1, 1);

INSERT INTO TeachingAssignment (idSubjectGroup, idAcademic)
VALUES (2, 2);

CREATE TABLE Record (
    idRecord INT AUTO_INCREMENT PRIMARY KEY,
    idStudent INT NOT NULL,
    idSubjectGroup INT NOT NULL,
    hoursCount INT NULL,
    reportPath VARCHAR(100) NULL,
    presentationPath VARCHAR(100) NULL,
    idTerm INT,
    FOREIGN KEY (idStudent) REFERENCES Student(idStudent),
    FOREIGN KEY (idSubjectGroup) REFERENCES SubjectGroup(idSubjectGroup),
    FOREIGN KEY (idTerm) REFERENCES Term(idTerm)
);

INSERT INTO Record (idStudent, idSubjectGroup, hoursCount, reportPath, presentationPath, idTerm) VALUES
(1, 1, NULL, NULL, NULL, 1);

INSERT INTO Record (idStudent, idSubjectGroup, hoursCount, reportPath, presentationPath, idTerm) VALUES
(2, 1, NULL, NULL, NULL, 1);

INSERT INTO Record (idStudent, idSubjectGroup, hoursCount, reportPath, presentationPath, idTerm) VALUES
(3, 2, NULL, NULL, NULL, 1);

INSERT INTO Record (idStudent, idSubjectGroup, hoursCount, reportPath, presentationPath, idTerm) VALUES
(4, 2, NULL, NULL, NULL, 1);

CREATE TABLE InitialDocument (
    idInitialDocument INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(65),
    date DATE,
    delivered TINYINT(1) NOT NULL DEFAULT 0,
    status ENUM('ENTREGADO', 'NO_ENTREGADO', 'EN_REVISION') NOT NULL,
    filePath VARCHAR(255),
    observations VARCHAR(200),
    grade DECIMAL(4,2)
);

CREATE TABLE ReportDocument (
    idReportDocument INT AUTO_INCREMENT PRIMARY KEY,
    reportedHours INT NOT NULL,
    date DATE NOT NULL,
    grade DECIMAL(4,2) NOT NULL,
    name VARCHAR(65),
    delivered TINYINT(1) NOT NULL DEFAULT 0,
    status ENUM('ENTREGADO', 'NO_ENTREGADO', 'EN_REVISION') NOT NULL,
    filePath VARCHAR(255)
);

CREATE TABLE FinalDocument (
    idFinalDocument INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(65) NOT NULL,
    date DATE,
    delivered TINYINT(1) NOT NULL DEFAULT 0,
    status ENUM('ENTREGADO', 'NO_ENTREGADO', 'EN_REVISION') NOT NULL,
    filePath VARCHAR(255),
    observations VARCHAR(200),
    grade DECIMAL(4,2)
);

CREATE TABLE Delivery (
    idDelivery INT AUTO_INCREMENT PRIMARY KEY,
    idRecord INT NULL,
    name VARCHAR(100),
    startDate DATETIME,
    endDate DATETIME,
    deliveryType ENUM('INITIAL DOCUMENT','FINAL DOCUMENT','REPORT') NOT NULL,
    idInitialDocument INT,
    idFinalDocument INT,
    idReportDocument INT,
    FOREIGN KEY (idRecord) REFERENCES Record(idRecord),
    FOREIGN KEY (idInitialDocument) REFERENCES InitialDocument(idInitialDocument),
    FOREIGN KEY (idFinalDocument) REFERENCES FinalDocument(idFinalDocument),
    FOREIGN KEY (idReportDocument) REFERENCES ReportDocument(idReportDocument)
);

INSERT INTO delivery (startDate, endDate, deliveryType, name)
VALUES ('2025-06-10 00:00:00', '2025-06-13 23:59:59', 'INITIAL DOCUMENT', 'Constancia de seguro facultativo');

INSERT INTO delivery (startDate, endDate, deliveryType, name)
VALUES ('2025-06-10 00:00:00', '2025-06-17 23:59:59', 'INITIAL DOCUMENT', 'Horario');

CREATE TABLE PresentationEvaluation (
    idEvaluation INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(65),
    date DATE,
    grade DECIMAL(4,2),
    observations TEXT, 
    idRecord INT,
    FOREIGN KEY (idRecord) REFERENCES Record(idRecord)
);

CREATE TABLE LinkedOrganization (
    idLinkedOrganization INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45),
    status TINYINT(1),
    address VARCHAR(70),
    phone VARCHAR(10)
);

INSERT INTO LinkedOrganization (name, status, address, phone) VALUES
('Tech Innovators SA', 1, 'Av. Reforma 123, Ciudad de México', '5512345678'),
('Global Solutions MX', 1, 'Calle Independencia 456, Guadalajara', '3311223344');

INSERT INTO LinkedOrganization (name, status, address, phone) VALUES
('Innovación Digital SA de CV', 1, 'Blvd. Insurgentes Sur 789, CDMX', '5544332211');

INSERT INTO LinkedOrganization (name, status, address, phone)
VALUES ('Nómada Digital Tech', 1, 'Blvd. Innovación 200, Distrito Tec, Monterrey, N.L.', '8123459876');

CREATE TABLE ProjectManager (
    idProjectManager INT AUTO_INCREMENT PRIMARY KEY,
    idLinkedOrganization INT,
    firstName VARCHAR(45) NOT NULL,
    lastNameFather VARCHAR(45) NOT NULL,
    lastNameMother VARCHAR(45),
    position VARCHAR(50),
    email VARCHAR(60),
    phone VARCHAR(10) NOT NULL,
    FOREIGN KEY (idLinkedOrganization) REFERENCES LinkedOrganization(idLinkedOrganization)
);

INSERT INTO ProjectManager (idLinkedOrganization, firstName, lastNameFather, lastNameMother, position, email, phone) VALUES
(1, 'Ana', 'Ramírez', 'Santos', 'Jefe de Desarrollo de Soluciones Tecnológicas', 'ana.ramirez@techinnovators.mx', '5512345678'),
(2, 'Carlos', 'Domínguez', 'Morales', 'Coordinador de Transformación Digital', 'carlos.dominguez@globalsolutions.mx', '3311223344');

CREATE TABLE Coordinator (
    idCoordinator INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(45) NOT NULL,
    lastNameFather VARCHAR(45) NOT NULL,
    lastNameMother VARCHAR(45),
    email VARCHAR(60),
    idUser INT NOT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES UserAccount(idUser)
);

INSERT INTO Coordinator (firstName, lastNameFather, lastNameMother, email, idUser) VALUES
('Valentina', 'García', 'Hernández', 'valentina.coordinadora@uv.mx', 3);

CREATE TABLE Project (
    idProject INT AUTO_INCREMENT PRIMARY KEY,
    idRecord INT,
    idProjectManager INT,
    idLinkedOrganization INT,
    idCoordinator INT,
    name VARCHAR(50) NOT NULL,
    department VARCHAR(30),
    description VARCHAR(200),
    methodology VARCHAR(45),
    availability INT,
    FOREIGN KEY (idRecord) REFERENCES Record(idRecord),
    FOREIGN KEY (idProjectManager) REFERENCES ProjectManager(idProjectManager),
    FOREIGN KEY (idLinkedOrganization) REFERENCES LinkedOrganization(idLinkedOrganization),
    FOREIGN KEY (idCoordinator) REFERENCES Coordinator(idCoordinator)
);



INSERT INTO Project (idRecord, idProjectManager, idLinkedOrganization, idCoordinator, name, department, description, methodology, availability) VALUES
(1, 1, 1, 1, 'Optimización de Procesos Industriales', 'Desarrollo Backend', 'Implementación de mejoras para optimizar la cadena de producción.', 'Lean Manufacturing', 3);

DELIMITER $$

CREATE TRIGGER after_project_update_student
AFTER UPDATE ON Project
FOR EACH ROW
BEGIN
    
    IF NEW.idRecord IS NOT NULL AND (OLD.idRecord IS NULL OR OLD.idRecord <> NEW.idRecord) THEN
        UPDATE Student s
        JOIN Record r ON s.idStudent = r.idStudent
        SET s.isAssignedToProject = 1
        WHERE r.idRecord = NEW.idRecord;
    END IF;
END $$

DELIMITER ;

INSERT INTO Project (
    idRecord,
    idProjectManager,
    idLinkedOrganization,
    idCoordinator,
    name,
    department,
    description,
    methodology,
    availability
) VALUES (
    NULL,
    2,
    2,
    1,
    'Proyecto Innovación Digital',
    'Backend',
    'Desarrollo de un sistema backend para gestión de usuarios y autenticación.',
    'Agile',
    3
);

UPDATE Project
SET idRecord = 3, availability = availability - 1
WHERE idProject = 2;

CREATE USER 'pp_admin'@'localhost' IDENTIFIED BY 'Ppsyst3m!';
GRANT ALL PRIVILEGES ON professional_practices.* TO 'pp_admin'@'localhost';
FLUSH PRIVILEGES;