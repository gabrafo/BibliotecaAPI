CREATE TABLE IF NOT EXISTS livro(
   id_livro SERIAL PRIMARY KEY,
   nome VARCHAR(45) NOT NULL,
   autor VARCHAR(45) NOT NULL,
   quantidade INTEGER NOT NULL,
   data_lancamento DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS pessoa (
    id_pessoa SERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    cep VARCHAR(9) NOT NULL
);

CREATE TABLE IF NOT EXISTS pessoa_autenticada (
    id_pessoa_autenticada SERIAL PRIMARY KEY,
    id_pessoa INTEGER NOT NULL,
    nome_de_usuario VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT FK_pessoa_autenticada FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE IF NOT EXISTS Emprestimo (
    id_livro INTEGER REFERENCES livro(id_livro),
    id_pessoa INTEGER REFERENCES pessoa(id_pessoa),
    CONSTRAINT FK_livro FOREIGN KEY (id_livro) REFERENCES livro(id_livro),
    CONSTRAINT FK_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
);
