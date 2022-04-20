CREATE SEQUENCE IF NOT EXISTS users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE USERS
(
  ID                 BIGINT DEFAULT nextval('users_id_seq'::regclass) PRIMARY KEY NOT NULL,
  USER_NAME          VARCHAR(255)                                                 NOT NULL,
  PASSWORD           VARCHAR(255)                                                 NOT NULL,
  EMAIL              VARCHAR(255)                                                 NOT NULL,
  CREATED_BY         VARCHAR(50)                                                  ,
  CREATED_DATE       timestamp                                                    NOT NULL,
  LAST_MODIFIED_BY   VARCHAR(50),
  LAST_MODIFIED_DATE timestamp
);
COMMENT ON COLUMN USERS.EMAIL
IS 'Эл. Почта';
COMMENT ON COLUMN USERS.PASSWORD
    IS 'Пароль';
COMMENT ON COLUMN USERS.USER_NAME
IS 'Логни';
COMMENT ON TABLE USERS
IS 'Пользователь';

CREATE SEQUENCE IF NOT EXISTS category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE CATEGORY
(
  ID                  BIGINT DEFAULT nextval('category_id_seq'::regclass) PRIMARY KEY NOT NULL,
  NAME                VARCHAR(255)                                                    NOT NULL,
  CREATED_BY          VARCHAR(50)                                                    ,
  CREATED_DATE        timestamp                                                       NOT NULL,
  LAST_MODIFIED_BY    VARCHAR(50),
  LAST_MODIFIED_DATE  timestamp
);
COMMENT ON COLUMN CATEGORY.NAME
IS 'Полное наименование';
COMMENT ON TABLE CATEGORY
IS 'Подразделение';

CREATE SEQUENCE IF NOT EXISTS article_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE ARTICLE
(
  ID                 BIGINT DEFAULT nextval('article_id_seq'::regclass) PRIMARY KEY NOT NULL,
  TITLE              VARCHAR(255)                                                   NOT NULL,
  BODY               VARCHAR(255)                                                   NOT NULL,
  CREATED_BY         VARCHAR(50)                                                    ,
  CREATED_DATE       timestamp                                                      NOT NULL,
  LAST_MODIFIED_BY   VARCHAR(50),
  LAST_MODIFIED_DATE timestamp
);
COMMENT ON COLUMN ARTICLE.TITLE
IS 'TITLE';
COMMENT ON COLUMN ARTICLE.BODY
IS 'BODY';

