create table fabricante(
cnpj char(14) not null,
nome varchar(100) not null,
nome_fantasia varchar(100) null,
constraint PK_cnpj_fabricante primary key (cnpj)
);

create table tipo(
id serial not null,
nome varchar(100) not null,
constraint PK_id_tipo primary key (id)
);

create table modelo(
id serial not null,
nome varchar(100) not null,
constraint PK_id_modelo primary key (id)
);

create table caracteristica(
id serial not null,
nome varchar(100) not null,
valor float null,
constraint PK_id_caracteristica primary key (id)
);

create table cor(
id_caracteristica int not null,
constraint PK_id_caracteristica_cor primary key (id_caracteristica),
constraint FK_id_caracteristica_cor foreign key (id_caracteristica) references caracteristica (id)
);

create table madeira(
id_caracteristica int not null,
constraint PK_id_caracteristica_madeira primary key (id_caracteristica),
constraint FK_id_caracteristica_madeira foreign key (id_caracteristica) references caracteristica (id)
);

create table instrumento(
id serial not null,
id_caracteristica int not null,
id_tipo int not null,
id_modelo int not null,
cnpj_fabricante char(14) not null,
constraint PK_id_instrumento primary key (id),
constraint FK_id_caracteristica_instrumento foreign key (id_caracteristica) references caracteristica (id),
constraint FK_id_tipo_instrumento foreign key (id_tipo) references tipo (id),
constraint FK_id_modelo_instrumento foreign key (id_modelo) references modelo (id),
constraint FK_cnpj_fabricante_instrumento foreign key (cnpj_fabricante) references fabricante (cnpj)
);

create table inst_caract(
id serial not null,
id_instrumento int not null,
id_caracteristica int not null,
qtd int not null,
constraint PK_id_inst_caract primary key (id),
constraint FK_id_instrumento_inst_caract foreign key (id_instrumento) references instrumento(id),
constraint FK_id_caracteristica_inst_caract foreign key (id_caracteristica) references caracteristica (id)
);



