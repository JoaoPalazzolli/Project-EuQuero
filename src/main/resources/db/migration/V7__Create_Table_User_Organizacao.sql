-- Table: public.user_organizacao

-- DROP TABLE IF EXISTS public.user_organizacao;

CREATE TABLE IF NOT EXISTS public.user_organizacao
(
    user_id bigint NOT NULL,
    organizacao_id bigint NOT NULL,
    CONSTRAINT fkfoo82lf8q54al87elp26maamq FOREIGN KEY (organizacao_id)
        REFERENCES public.organizacao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhc2nx2uk66y4w4o7yxy44n542 FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

