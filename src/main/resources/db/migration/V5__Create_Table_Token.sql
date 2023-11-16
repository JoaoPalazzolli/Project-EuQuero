-- Table: public.token

-- DROP TABLE IF EXISTS public.token;

CREATE SEQUENCE IF NOT EXISTS token_id_seq;

CREATE TABLE IF NOT EXISTS public.token
(
    id bigint NOT NULL DEFAULT nextval('token_id_seq'::regclass),
    create_at timestamp(6) without time zone NOT NULL,
    access_token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    refresh_token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token_type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    verified boolean NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT token_pkey PRIMARY KEY (id),
    CONSTRAINT fkj8rfw4x0wjjyibfqq566j4qng FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT token_token_type_check CHECK (token_type::text = 'BEARER'::text)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.token
    OWNER to postgres;