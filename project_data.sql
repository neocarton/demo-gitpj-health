CREATE TABLE IF NOT EXISTS state (
    id SMALLINT,
    last_page INT NOT NULL,
    last_offset INT NOT NULL,
    last_proceeded_count INT NOT NULL DEFAULT 0,
    total_proceeded_count INT NOT NULL DEFAULT 0,
    PRIMARY KEY(id),
);

CREATE TABLE IF NOT EXISTS data (
    id VARCHAR(200) NOT NULL,
    object_id VARCHAR(100) NOT NULL,
    key VARCHAR(100) NOT NULL,
    value CLOB(100K),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
);

CREATE INDEX IF NOT EXISTS data_object_id_idx ON data (object_id);

CREATE INDEX IF NOT EXISTS data_key_idx ON data (key);

CREATE INDEX IF NOT EXISTS data_object_id_key_idx ON data (object_id, key);

CREATE INDEX IF NOT EXISTS data_created_at_idx ON data (created_at);

CREATE INDEX IF NOT EXISTS data_updated_at_idx ON data (updated_at);

CREATE VIEW IF NOT EXISTS progress AS (
    SELECT projects, commits, issues, pull_requests, contributors
    FROM (SELECT count(*) AS projects FROM data
        WHERE key = 'full_name')
    JOIN (SELECT count(*) AS commits FROM data
        WHERE key = 'full_name' AND object_id NOT IN (SELECT DISTINCT object_id FROM data WHERE key = 'commit.count'))
    JOIN (SELECT count(*) AS pull_requests FROM data
        WHERE key = 'full_name' AND object_id NOT IN (SELECT DISTINCT object_id FROM data WHERE key = 'pull_request.count'))
    JOIN (SELECT count(*) AS issues FROM data
        WHERE key = 'full_name' AND object_id NOT IN (SELECT DISTINCT object_id FROM data WHERE key = 'issue.count'))
    JOIN (SELECT count(*) AS contributors FROM data
        WHERE key = 'full_name' AND object_id NOT IN (SELECT DISTINCT object_id FROM data WHERE key = 'contributor.count'))
);

CREATE VIEW IF NOT EXISTS repos_metric AS (
    SELECT n.object_id, owner, repos, IFNULL(forks, 0) AS forks, IFNULL(stars, 0) AS stars,
        IFNULL(commits_per_day, 0) as commits_per_day,
        IFNULL(commits_per_day_per_person, 0) AS commits_per_day_per_person,
        IFNULL(avg_pr_open_time, 0) AS avg_pr_open_time,
        IFNULL(avg_pr_merge_time, 0) AS avg_pr_merge_time,
        IFNULL(avg_comments_per_pr, 0) AS avg_comments_per_pr,
        IFNULL(avg_iss_open_time, 0) AS avg_iss_open_time,
        IFNULL(avg_iss_close_time, 0) AS avg_iss_close_time,
        IFNULL(avg_iss_open_ratio, 0) AS avg_iss_open_ratio,
        IFNULL(avg_iss_close_ratio, 0) AS avg_iss_close_ratio
    FROM (SELECT object_id, value AS repos FROM data WHERE key ='name') n
    JOIN (SELECT object_id, value AS owner FROM data WHERE key ='owner') o ON o.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS forks FROM data WHERE key ='forks') f ON f.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS stars FROM data WHERE key ='stars') s ON s.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS commits_per_day FROM data WHERE key ='commit.daily_avg') c ON c.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS commits_per_day_per_person FROM data WHERE key ='contributor.avg_commits') ct ON ct.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS avg_pr_open_time FROM data WHERE key ='pull_request.avg_open_time_s') pro ON pro.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS avg_pr_merge_time FROM data WHERE key ='pull_request.avg_merge_time_s') prm ON prm.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS avg_comments_per_pr FROM data WHERE key ='pull_request.avg_comments') prc ON prc.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS avg_iss_open_time FROM data WHERE key ='issue.avg_open_time_s') iso ON iso.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS avg_iss_close_time FROM data WHERE key ='issue.avg_close_time_s') isc ON isc.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS avg_iss_open_ratio FROM data WHERE key ='issue.open_ratio') isor ON isor.object_id = n.object_id
    LEFT JOIN (SELECT object_id, CAST(value AS DOUBLE) AS avg_iss_close_ratio FROM data WHERE key ='issue.close_ratio') iscr ON iscr.object_id = n.object_id
    ORDER BY stars DESC
);

CREATE VIEW IF NOT EXISTS repos_metric_aggr AS (
    SELECT AVG(forks) AS avg_forks, AVG(stars) AS avg_stars,
        AVG(commits_per_day) AS avg_commits_per_day,
        AVG(commits_per_day_per_person) AS avg_commits_per_day_per_person,
        AVG(avg_pr_open_time) AS avg_pr_open_time,
        AVG(avg_pr_merge_time) AS avg_pr_merge_time,
        AVG(avg_iss_open_time) AS avg_iss_open_time,
        AVG(avg_iss_close_time) AS avg_iss_close_time,
        AVG(avg_iss_open_ratio) AS avg_iss_open_ratio,
        AVG(avg_iss_close_ratio) AS avg_iss_close_ratio,
        MAX(forks) AS max_forks, MAX(stars) AS max_stars,
        MAX(commits_per_day) AS max_commits_per_day,
        MAX(commits_per_day_per_person) AS max_commits_per_day_per_person,
        MAX(avg_pr_open_time) AS max_pr_open_time,
        MAX(avg_pr_merge_time) AS max_pr_merge_time,
        MAX(avg_iss_open_time) AS max_iss_open_time,
        MAX(avg_iss_close_time) AS max_iss_close_time,
        MAX(avg_iss_open_ratio) AS max_iss_open_ratio,
        MAX(avg_iss_close_ratio) AS max_iss_close_ratio
    FROM repos_metric
);

CREATE VIEW IF NOT EXISTS repos_metric_ratio AS (
    SELECT object_id, owner, repos,
        forks/max_forks AS fork_ratio,
        stars/max_stars AS star_ratio,
        commits_per_day/max_commits_per_day AS commits_per_day_ratio,
        commits_per_day_per_person/max_commits_per_day_per_person AS commits_per_day_per_person_ratio,
        m.avg_pr_open_time/mm.max_pr_open_time AS pr_open_time_ratio,
        m.avg_pr_merge_time/mm.max_pr_merge_time AS pr_merge_time_ratio,
        m.avg_iss_open_time/mm.max_iss_open_time AS iss_open_time_ratio,
        m.avg_iss_close_time/mm.max_iss_close_time AS iss_close_time_ratio
    FROM repos_metric m, repos_metric_aggr mm
);

CREATE VIEW IF NOT EXISTS repos_health_score AS (
    SELECT object_id, owner, repos,
        ROUND(fork_ratio * star_ratio * pr_merge_time_ratio * iss_close_time_ratio, 4) AS health_score,
        ROUND((fork_ratio + star_ratio + pr_merge_time_ratio + iss_close_time_ratio) / 4, 4) AS health_score_by_averaging,
        fork_ratio, star_ratio, commits_per_day_ratio, commits_per_day_per_person_ratio,
        pr_open_time_ratio, pr_merge_time_ratio, iss_open_time_ratio, iss_close_time_ratio
    FROM repos_metric_ratio
    ORDER BY health_score DESC, health_score_by_averaging DESC
);
