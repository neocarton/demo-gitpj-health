# Introduction

The project evaluate GitHub project health by looking at number star and fork.

# Getting Started

### Configuration

Set valid github Personal Token to src/main/resources/application.properties as follows:
```
app.github.access_token=7066ec5c7baf66cdad7057c474ac4cf0e00c0bd1
```

### Build

./make

Or

./gradlew clean build

### Execute

./run

Or

java -jar ./build/libs/ProjectHealth-0.0.1-SNAPSHOT.jar

### Get results

There is a sample result with 100 repositories in [repos_health_scores.csv](repos_health_scores.csv)

In order to access repository health scores, open browser with URL http://localhost:8082

Use following information to login
```
Saved Settings: Generic H2 (Embedded)
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:./project_data
User Name: sa
Password: sa
```

Execute following SQL to query project health score
```
SELECT * FROM REPOS_HEALTH_SCORE
```
NOTE: It take some time to join all the data from key-value structure to table and calculate health scores.

### Solution

For coding convenience, the project utilize Spring Framework and Spring Data.

The solution is to have Loaders that are triggered by schedulers.
When triggered, loaders start to gather data from github.com,
and then store it onto local H2 database in key-value structure.
Loaders can restart from their previous state.

* ProjectLoader is responsible for loading the top 1000 repositories from github.com, it loads 100 repositories at a time.
* CommitStatLoader loads yearly commit statistics for each repository, as soon as the first 100 repositories are loaded, it proceeds 10 repositories at a time.
* IssueLoader loads recent 300 issues of each repository for evaluation, it proceeds 10 repositories at a time.
* PullRequestLoader loads recent 300 pull-requests of each repository for evaluation, it proceeds 10 repositories at a time.
* ContributorStatLoader loads contributors and their commits for each repository.

### Issues

There are some obvious issues

* The implementation seem a little slow
* Data is not optimised properly, query time takes a little too long
* Final result is not quite correct, due to data parsing bugs, wrong data source, evaluation formulas need to be reconsidered

### Improvements

In other to improve above issues

* Applying multi-tasking to improve loading time
* Using data-flow framework or event-sourcing design pattern for better state management, especially for multi-tasking application
* Only storing fact (no calculation on the fly), aggregating raw data into structured table instead of view
* Aggregating collected metric to calculate final result
