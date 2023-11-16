-- Table: public.organizacao

-- DROP TABLE IF EXISTS public.organizacao;

CREATE SEQUENCE IF NOT EXISTS organizacao_id_seq;

CREATE TABLE IF NOT EXISTS public.organizacao
(
    tipo_organizacao character varying(31) COLLATE pg_catalog."default" NOT NULL,
    id bigint NOT NULL DEFAULT nextval('organizacao_id_seq'::regclass),
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descricao text COLLATE pg_catalog."default" NOT NULL,
    cnpj character varying(255) COLLATE pg_catalog."default",
    objetivo character varying(255) COLLATE pg_catalog."default",
    url_imagem character varying(255) COLLATE pg_catalog."default",
    url_site character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT organizacao_pkey PRIMARY KEY (id),
    CONSTRAINT uk_hhnmvbq76b9xb79jpbsaneksl UNIQUE (cnpj)
)

TABLESPACE pg_default;

