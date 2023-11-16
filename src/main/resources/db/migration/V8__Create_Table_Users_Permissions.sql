-- Table: public.user_permission

-- DROP TABLE IF EXISTS public.user_permission;

CREATE TABLE IF NOT EXISTS public.user_permission
(
    user_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    expire_at timestamp(6) without time zone,
    plano_ativado boolean,
    CONSTRAINT user_permission_pkey PRIMARY KEY (permission_id, user_id),
    CONSTRAINT fk1r9shydjvgeefuwsrhrcqtkxd FOREIGN KEY (permission_id)
        REFERENCES public.permissions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkn8ba4v3gvw1d82t3hofelr82t FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

