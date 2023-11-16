-- Table: public.permissions

-- DROP TABLE IF EXISTS public.permissions;

CREATE SEQUENCE IF NOT EXISTS permissions_id_seq;

CREATE TABLE IF NOT EXISTS public.permissions
(
    id bigint NOT NULL DEFAULT nextval('permissions_id_seq'::regclass),
    descricao character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT permissions_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

