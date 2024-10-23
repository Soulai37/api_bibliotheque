CREATE SCHEMA IF NOT EXISTS api_bibliotheque;
USE api_bibliotheque;

CREATE TABLE utilisateur(
	id INT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    type BOOLEAN
);

CREATE TABLE livre(
	isbn varchar(50) PRIMARY KEY,
    nom varchar(100),
    auteur varchar(50),
    quantite INT DEFAULT 0
);

CREATE TABLE emprunt(
	id INT PRIMARY KEY,
    isbn_livre VARCHAR(50),
    id_utilisateur INT,
    date_emprunt DATE,
    date_retour DATE,
    FOREIGN KEY (isbn_livre) REFERENCES livre(isbn),
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id)
);

INSERT INTO utilisateur (id, nom, type) VALUES (1, 'Alice Dupont', FALSE);
INSERT INTO utilisateur (id, nom, type) VALUES (2, 'Jean Martin', FALSE);
INSERT INTO utilisateur (id, nom, type) VALUES (3, 'Sophie Bernard', FALSE);
INSERT INTO utilisateur (id, nom, type) VALUES (4, 'David Leroy', TRUE);

INSERT INTO livre (isbn, nom, auteur, quantite) VALUES ('978-1234567890', 'Les Misérables', 'Victor Hugo', 5);
INSERT INTO livre (isbn, nom, auteur, quantite) VALUES ('978-0987654321', 'Pierre et Jean', 'Guy de Maupassant', 3);
INSERT INTO livre (isbn, nom, auteur, quantite) VALUES ('978-1122334455', '1984', 'George Orwell', 10);
INSERT INTO livre (isbn, nom, auteur, quantite) VALUES ('978-2233445566', 'Le Librarire', 'Gérard Bessette', 4);

INSERT INTO emprunt (id, isbn_livre, id_utilisateur, date_emprunt, date_retour) VALUES (1, '978-1234567890', 1, '2024-01-10', '2024-02-10');
INSERT INTO emprunt (id, isbn_livre, id_utilisateur, date_emprunt, date_retour) VALUES (2, '978-0987654321', 2, '2024-03-12', '2024-04-12');
INSERT INTO emprunt (id, isbn_livre, id_utilisateur, date_emprunt, date_retour) VALUES (3, '978-1122334455', 3, '2024-05-15', '2024-06-15');
INSERT INTO emprunt (id, isbn_livre, id_utilisateur, date_emprunt, date_retour) VALUES (4, '978-2233445566', 4, '2024-07-20', '2024-08-20');

