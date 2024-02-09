CREATE TABLE public.usr(
                           id bigint,
                           email varchar(150) NOT NULL,
                           password varchar(150) NOT NULL,
                           username varchar(100) NOT NULL,
                           created_at date NOT NULL DEFAULT CURRENT_DATE,
                           description varchar(512),
                           avatar_url varchar(200),
                           rating numeric DEFAULT 5,
                           CONSTRAINT user_id PRIMARY KEY (id)

);


CREATE TABLE public.theme(
                             id bigint,
                             name varchar(50) NOT NULL,
                             CONSTRAINT theme_pk PRIMARY KEY (id),
                             CONSTRAINT theme_name_unique UNIQUE (name)

);


CREATE TABLE public.post(
                            id bigint,
                            theme_id bigint,
                            post_type_id bigint,
                            user_id bigint,
                            updated_at timestamp,
                            heading varchar(50) NOT NULL,
                            thumbnail_url varchar(200),
                            likes_amount integer NOT NULL DEFAULT 0,
                            post_text text NOT NULL,
                            created_at timestamp NOT NULL DEFAULT now(),
                            CONSTRAINT post_id PRIMARY KEY (id)

);


CREATE TABLE public.user_role(
                                 id bigint,
                                 name varchar(50),
                                 CONSTRAINT user_role_id PRIMARY KEY (id)

);


CREATE TABLE public.comment(
                               id bigint,
                               post_id bigint,
                               user_id bigint,
                               created_at timestamp NOT NULL DEFAULT now(),
                               updated_at timestamp,
                               likes_amount integer DEFAULT 0,
                               comment_text text NOT NULL,
                               CONSTRAINT comment_pk PRIMARY KEY (id)

);


CREATE TABLE public.tag(
                           id bigint,
                           parent_id bigint,
                           name varchar(50) NOT NULL,
                           CONSTRAINT tag_pk PRIMARY KEY (id)

);


CREATE TABLE public.post_type(
                                 id bigint,
                                 name varchar(50),
                                 CONSTRAINT post_type_pk PRIMARY KEY (id)

);


CREATE TABLE public.user_user_role(
                                      user_role_id bigint,
                                      user_id bigint,
                                      CONSTRAINT many_user_role_has_many_user_pk PRIMARY KEY (user_role_id,user_id)

);


ALTER TABLE public.user_user_role ADD CONSTRAINT user_role_fk FOREIGN KEY (user_role_id)
    REFERENCES public.user_role (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.user_user_role ADD CONSTRAINT usr_fk FOREIGN KEY (user_id)
    REFERENCES public.usr (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.comment ADD CONSTRAINT post_fk FOREIGN KEY (post_id)
    REFERENCES public.post (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.post ADD CONSTRAINT theme_fk FOREIGN KEY (theme_id)
    REFERENCES public.theme (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.post ADD CONSTRAINT post_type_fk FOREIGN KEY (post_type_id)
    REFERENCES public.post_type (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE public.tag_post(
                                id_tag bigint,
                                post_id bigint,
                                CONSTRAINT tag_post_pk PRIMARY KEY (id_tag,post_id)

);

ALTER TABLE public.tag_post ADD CONSTRAINT tag_fk FOREIGN KEY (id_tag)
    REFERENCES public.tag (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.tag_post ADD CONSTRAINT post_fk FOREIGN KEY (post_id)
    REFERENCES public.post (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE public.user_role_theme(
                                       user_role_id bigint,
                                       theme_id bigint,
                                       CONSTRAINT user_role_theme_pk PRIMARY KEY (user_role_id,theme_id)

);

ALTER TABLE public.user_role_theme ADD CONSTRAINT user_role_fk FOREIGN KEY (user_role_id)
    REFERENCES public.user_role (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.user_role_theme ADD CONSTRAINT theme_fk FOREIGN KEY (theme_id)
    REFERENCES public.theme (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.post ADD CONSTRAINT usr_fk FOREIGN KEY (user_id)
    REFERENCES public.usr (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.comment ADD CONSTRAINT usr_fk FOREIGN KEY (user_id)
    REFERENCES public.usr (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

CREATE TABLE public.theme_tag(
                                 theme_id bigint,
                                 id_tag bigint,
                                 CONSTRAINT theme_tag_pk PRIMARY KEY (theme_id,id_tag)

);

ALTER TABLE public.theme_tag ADD CONSTRAINT theme_fk FOREIGN KEY (theme_id)
    REFERENCES public.theme (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.theme_tag ADD CONSTRAINT tag_fk FOREIGN KEY (id_tag)
    REFERENCES public.tag (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE public.tag ADD CONSTRAINT tag_parent_fk FOREIGN KEY (parent_id)
    REFERENCES public.tag (id) MATCH FULL
    ON DELETE SET NULL ON UPDATE CASCADE;



