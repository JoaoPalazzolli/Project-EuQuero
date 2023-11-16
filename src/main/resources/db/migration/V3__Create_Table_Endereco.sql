-- Table: public.endereco

-- DROP TABLE IF EXISTS public.endereco;

CREATE SEQUENCE IF NOT EXISTS endereco_id_seq;

CREATE TABLE IF NOT EXISTS public.endereco
(
    id bigint NOT NULL DEFAULT nextval('endereco_id_seq'::regclass),
    bairro character varying(255) COLLATE pg_catalog."default",
    cep character varying(255) COLLATE pg_catalog."default" NOT NULL,
    complemento character varying(255) COLLATE pg_catalog."default",
    ddd character varying(255) COLLATE pg_catalog."default",
    localidade character varying(255) COLLATE pg_catalog."default" NOT NULL,
    logradouro character varying(255) COLLATE pg_catalog."default",
    numero character varying(255) COLLATE pg_catalog."default",
    uf character varying(255) COLLATE pg_catalog."default" NOT NULL,
    organizacao_id bigint NOT NULL,
    CONSTRAINT endereco_pkey PRIMARY KEY (id),
    CONSTRAINT fkqmm3dsragwwtctf210nk1sm56 FOREIGN KEY (organizacao_id)
        REFERENCES public.organizacao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

