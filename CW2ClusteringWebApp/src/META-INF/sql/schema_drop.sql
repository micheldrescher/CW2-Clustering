-- MUST be executed as role clustering
set role clustering;

ALTER TABLE clustering.response_score DROP CONSTRAINT fk_response_score_scores_id;

ALTER TABLE clustering.response_score DROP CONSTRAINT fk_response_score_jparesponse_id;

ALTER TABLE clustering.response DROP CONSTRAINT fk_response_respondent_id;

ALTER TABLE clustering.response_score DROP CONSTRAINT response_score_pkey;

ALTER TABLE clustering.sequence DROP CONSTRAINT sequence_pkey;

ALTER TABLE clustering.score DROP CONSTRAINT score_pkey;

ALTER TABLE clustering.response DROP CONSTRAINT response_pkey;

ALTER TABLE clustering.respondent DROP CONSTRAINT respondent_pkey;

ALTER TABLE clustering.users DROP CONSTRAINT users_pkey;

DROP TABLE clustering.respondent;

DROP TABLE clustering.sequence;

DROP TABLE clustering.response;

DROP TABLE clustering.score;

DROP TABLE clustering.response_score;

DROP TABLE clustering.users;

DROP schema clustering;