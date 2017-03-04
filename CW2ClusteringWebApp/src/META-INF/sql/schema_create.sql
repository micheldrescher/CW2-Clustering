-- MUST be executed as role clustering
set role clustering;

CREATE SCHEMA clustering authorization clustering;

CREATE TABLE clustering.users (
		id INT8 NOT NULL,
		username VARCHAR(40) NOT NULL,
		password VARCHAR(40) NOT NULL
	);

CREATE TABLE clustering.respondent (
		id INT8 NOT NULL,
		affiliation VARCHAR(255),
		createddate TIMESTAMP,
		email VARCHAR(255),
		firstname VARCHAR(255),
		lastname VARCHAR(255),
		modifieddate TIMESTAMP
	);

CREATE TABLE clustering.sequence (
		seq_name VARCHAR(50) NOT NULL,
		seq_count NUMERIC(38 , 0)
	);

CREATE TABLE clustering.response (
		id INT8 NOT NULL,
		createddate TIMESTAMP,
		modifieddate TIMESTAMP,
		project VARCHAR(255),
		website VARCHAR(128),
		validated BOOL,
		respondent_id INT8
	);

CREATE TABLE clustering.score (
		id INT8 NOT NULL,
		characteristic VARCHAR(255),
		createddate TIMESTAMP,
		modifieddate TIMESTAMP,
		value INT4
	);

CREATE TABLE clustering.response_score (
		jparesponse_id INT8 NOT NULL,
		scores_id INT8 NOT NULL
	);

ALTER TABLE clustering.users ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE clustering.response_score ADD CONSTRAINT response_score_pkey PRIMARY KEY (jparesponse_id, scores_id);

ALTER TABLE clustering.sequence ADD CONSTRAINT sequence_pkey PRIMARY KEY (seq_name);

ALTER TABLE clustering.score ADD CONSTRAINT score_pkey PRIMARY KEY (id);

ALTER TABLE clustering.response ADD CONSTRAINT response_pkey PRIMARY KEY (id);

ALTER TABLE clustering.respondent ADD CONSTRAINT respondent_pkey PRIMARY KEY (id);

ALTER TABLE clustering.response_score ADD CONSTRAINT fk_response_score_scores_id FOREIGN KEY (scores_id)
	REFERENCES clustering.score (id);

ALTER TABLE clustering.response_score ADD CONSTRAINT fk_response_score_jparesponse_id FOREIGN KEY (jparesponse_id)
	REFERENCES clustering.response (id);

ALTER TABLE clustering.response ADD CONSTRAINT fk_response_respondent_id FOREIGN KEY (respondent_id)
	REFERENCES clustering.respondent (id);
