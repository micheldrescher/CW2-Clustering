-- MUST be executed as role clustering
set role clustering;

delete from clustering.response_score;
delete from clustering.score;
delete from clustering.response;
delete from clustering.respondent;
delete from clustering.sequence;
