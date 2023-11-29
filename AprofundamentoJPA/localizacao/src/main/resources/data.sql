create table tb_cidade (
    id_cidade bigint not null primary key,
    nome varchar(50) not null,
    qtd_habitantes bigint
);

   insert into tb_cidade (id_cidade, nome, qtd_habitantes)
   values
    (1, 'Sao Paulo', 1234567),
    (2, 'Rio de Janeiro', 33333),
    (3, 'Porto Velho', 00000),
    (4, 'São Gonzalo', 11);
