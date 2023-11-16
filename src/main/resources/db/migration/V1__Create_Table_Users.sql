-- Table: public.users

-- DROP TABLE IF EXISTS public.users;
CREATE SEQUENCE users_id_seq;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    cpf character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(11) COLLATE pg_catalog."default" NOT NULL,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    enabled boolean,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
    CONSTRAINT uk_7kqluf7wl0oxs7n90fpya03ss UNIQUE (cpf),
    CONSTRAINT uk_du5v5sr43g5bfnji4vb8hg5s3 UNIQUE (phone)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;