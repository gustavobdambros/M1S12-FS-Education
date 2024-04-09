CREATE TABLE public.alunos
(
    id         bigserial    NOT NULL,
    nascimento date,
    nome       varchar(150) NOT NULL,
    CONSTRAINT alunos_pkey PRIMARY KEY (id)
);

CREATE TABLE public.professores
(
    id   bigserial    NOT NULL,
    nome varchar(150) NOT NULL,
    CONSTRAINT professores_pkey PRIMARY KEY (id)
);

CREATE TABLE public.disciplinas
(
    id             bigserial    NOT NULL,
    nome           varchar(150) NOT NULL,
    professores_id bigint       NOT NULL,
    CONSTRAINT disciplinas_pkey PRIMARY KEY (id),
    CONSTRAINT fkhqdmr34mwweysnoij2sgqaqln FOREIGN KEY (professores_id) REFERENCES public.professores (id)
);

CREATE TABLE public.disciplina_matriculas
(
    id             bigserial     NOT NULL,
    aluno_id       bigint        NOT NULL,
    disciplina_id  bigint        NOT NULL,
    data_matricula date          NOT NULL DEFAULT now(),
    media_final    numeric(5, 2) NULL     DEFAULT 0.00,
    CONSTRAINT disciplina_matriculas_pkey PRIMARY KEY (id),
    CONSTRAINT fk1v0t92ifosbb7tqdak8g27b3i FOREIGN KEY (disciplina_id) REFERENCES public.disciplinas (id),
    CONSTRAINT fk38om78bdkmdglfcxmsiba9pm5 FOREIGN KEY (aluno_id) REFERENCES public.alunos (id)
);

CREATE TABLE public.notas
(
    id                      bigserial      NOT NULL,
    disciplina_matricula_id bigint         NOT NULL,
    professor_id            bigint         NOT NULL,
    nota                    numeric(5, 2)  NOT NULL DEFAULT 0.00,
    coeficiente             numeric(19, 6) NOT NULL DEFAULT 0.00,
    CONSTRAINT notas_pkey PRIMARY KEY (id),
    CONSTRAINT fk26jiy8wcbftx8kfqn6siempme FOREIGN KEY (professor_id) REFERENCES public.professores (id),
    CONSTRAINT fksrcsmumtuhbgmi3cug6x0l5bj FOREIGN KEY (disciplina_matricula_id) REFERENCES public.disciplina_matriculas (id)
);