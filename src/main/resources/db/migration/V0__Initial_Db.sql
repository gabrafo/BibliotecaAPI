CREATE TABLE IF NOT EXISTS Livro(
                       id_livro SERIAL PRIMARY KEY,
                       nome VARCHAR(45) NOT NULL,
                       autor VARCHAR(45) NOT NULL,
                       quantidade INTEGER NOT NULL,
                       data_lancamento DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Pessoa (
                        id_pessoa SERIAL PRIMARY KEY,
                        nome VARCHAR(80) NOT NULL,
                        cep VARCHAR(9) NOT NULL
);

CREATE TABLE IF NOT EXISTS Emprestimo (
                            id_livro INTEGER REFERENCES Livro(id_livro),
                            id_pessoa INTEGER REFERENCES Pessoa(id_pessoa),
                            CONSTRAINT FK_Livro FOREIGN KEY (id_livro) REFERENCES Livro(id_livro),
                            CONSTRAINT FK_Pessoa FOREIGN KEY (id_pessoa) REFERENCES Pessoa(id_pessoa)
);
