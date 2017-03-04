-- MUST be executed as role postgres
create role clustering noinherit;
create role webapp login password 'T00M4nyP4$$w0rd$!' inherit;
create role michel login password 'Why3lv1sWhyM3?' inherit;
grant clustering to webapp;
grant clustering to michel;
create database clustering_db owner clustering;