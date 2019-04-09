# Estafet Microservices Scrum QA Functional Tests
This project contains the cucumber acceptance tests for the microservices scrum application. These are tests that focus on verifying the business functionality and therefore my "cut accross" multiple microservices

## Test Environment Setup
The test environment allows QAs and anybody interested in developing cross-cutting functional test. Functional tests for this application are written using the cucumber framework and can be executed as standard junit tests.

Functional tests are executed on released versions of the application (rather than the latest version on the master branch as with the local development environment). With this in mind, QAs will need to use a different ansible playbook to build the latest released version of the applications microservices.

Furthermore, as the cucumber tests are running locally (either on a Linux machine or Windows 10), we will need to setup port forwarding so that test implementations can connect to the database and message broker containers running in Openshift.

### Steps

#### Step #1
Clone the scrum repository to a directory of your choice.

```
git clone https://github.com/Estafet-LTD/estafet-microservices-scrum.git
```

#### Step #2
You will need to 

> Note:- If you are using minishift, it might be advisible to tweak the resources available (see above).

```
$ cd estafet-microservices-scrum/ansible
$ ansible-playbook create-local-test-environment-playbook.yml
```

#### Step #3
Just like the microservices, the cucumber tests are configured using environment variables. The way these environment variables are setup, will depend on whether you are operating in a windows or linux environment. Special attention must be paid to the `OPENSHIFT_HOST` variable as this is likely to vary depending on your specific local environment setup.

Setting up the environment variables is a lengthly process as each there are parameters for each microservice and its corresponding database. Fortunately this is a one time process that only has to be revisted when new microservices are added to the application.

##### Windows Environment Variables Setup
Below are the environment variables and their corresponding values that are required to run the tests on a Windows 10 environment. 
 
|Variable|Value|
|--------|-----|
|OPENSHIFT_HOST|192.168.99.100|
|BASIC_UI_URI|http://basic-ui-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
|JBOSS_A_MQ_BROKER_PASSWORD|amq|
|JBOSS_A_MQ_BROKER_URL|tcp://localhost:61616|
|JBOSS_A_MQ_BROKER_USER|amq|
|PROJECT_API_DB_PASSWORD|welcome1|
|PROJECT_API_DB_USER|postgres|
|PROJECT_API_JDBC_URL|jdbc:postgresql://localhost:5432/project-api|
|PROJECT_API_SERVICE_URI|http://project-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
|PROJECT_BURNDOWN_REPOSITORY_DB_PASSWORD|welcome1|
|PROJECT_BURNDOWN_REPOSITORY_DB_USER|postgres|
|PROJECT_BURNDOWN_REPOSITORY_JDBC_URL|jdbc:postgresql://localhost:5432/project-burndown|
|PROJECT_BURNDOWN_SERVICE_URI|http://project-burndown-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
|SPRINT_API_DB_PASSWORD|welcome1|
|SPRINT_API_DB_USER|postgres|
|SPRINT_API_JDBC_URL|jdbc:postgresql://localhost:5432/sprint-api|
|SPRINT_API_SERVICE_URI|http://sprint-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
|SPRINT_BURNDOWN_DB_PASSWORD|welcome1|
|SPRINT_BURNDOWN_DB_USER|postgres|
|SPRINT_BURNDOWN_JDBC_URL|jdbc:postgresql://localhost:5432/sprint-burndown|
|SPRINT_BURNDOWN_SERVICE_URI|http://sprint-burndown-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
|STORY_API_DB_PASSWORD|welcome1|
|STORY_API_DB_USER|postgres|
|STORY_API_JDBC_URL|jdbc:postgresql://localhost:5432/story-api|
|STORY_API_SERVICE_URI|http://story-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
|TASK_API_DB_PASSWORD|welcome1|
|TASK_API_DB_USER|postgres|
|TASK_API_JDBC_URL|jdbc:postgresql://localhost:5432/task-api|
|TASK_API_SERVICE_URI|http://task-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
 
##### Linux Environment Variables Setup
 Below are variable variables for linux. It might be easiest to copy the code below and add it to the `~/.profile` file.

```
export OPENSHIFT_HOST=192.168.99.100
export BASIC_UI_URI=http://basic-ui-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io
export JBOSS_A_MQ_BROKER_PASSWORD=amq
export JBOSS_A_MQ_BROKER_URL=tcp://localhost:61616
export JBOSS_A_MQ_BROKER_USER=amq
export PROJECT_API_DB_PASSWORD=welcome1
export PROJECT_API_DB_USER=postgres
export PROJECT_API_JDBC_URL=jdbc:postgresql://localhost:5432/project-api
export PROJECT_API_SERVICE_URI=http://project-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io|
export PROJECT_BURNDOWN_REPOSITORY_DB_PASSWORD=welcome1
export PROJECT_BURNDOWN_REPOSITORY_DB_USER= postgres
export PROJECT_BURNDOWN_REPOSITORY_JDBC_URL=jdbc:postgresql://localhost:5432/project-burndown
export PROJECT_BURNDOWN_SERVICE_URI=http://project-burndown-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io
export SPRINT_API_DB_PASSWORD=welcome1
export SPRINT_API_DB_USER=postgres
export SPRINT_API_JDBC_URL=jdbc:postgresql://localhost:5432/sprint-api
export SPRINT_API_SERVICE_URI=http://sprint-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io
export SPRINT_BURNDOWN_DB_PASSWORD=welcome1
export SPRINT_BURNDOWN_DB_USER=postgres
export SPRINT_BURNDOWN_JDBC_URL=jdbc:postgresql://localhost:5432/sprint-burndown
export SPRINT_BURNDOWN_SERVICE_URI=http://sprint-burndown-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io
export STORY_API_DB_PASSWORD=welcome1
export STORY_API_DB_USER=postgres
export STORY_API_JDBC_URL=jdbc:postgresql://localhost:5432/story-api
export STORY_API_SERVICE_URI=http://story-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io
export TASK_API_DB_PASSWORD=welcome1
export TASK_API_DB_USER=postgres
export TASK_API_JDBC_URL=jdbc:postgresql://localhost:5432/task-api
export TASK_API_SERVICE_URI=http://task-api-test-microservices-scrum.%OPENSHIFT_HOST%.nip.io
```

#### Step #5
The final setup step requires setting up the port forwarding for the database and message broker. Fortunately there are two scripts to make this process simpler.

Clone the qa repository to a directory of your choice.

```
git clone https://github.com/Estafet-LTD/estafet-microservices-scrum-qa.git
$ ./pf-broker.sh
$ ./pf-db.sh
```

Note:- You will need to run these scripts in different term shells as the processes must continue running for the port forwarding to work. It should also be noted that these scripts are linux scripts, so windows users must run these using the WSL bash shell.

### Executing the tests
The tests can now be executed either via a development IDE (e.g. Eclipse) or from the command line.

#### Executing via Eclipse
Within the QA project  src/test/java > Run As > Junit Test

![alt tag](https://github.com/Estafet-LTD/estafet-microservices-scrum/blob/master/ExecutingCucumberTestsOnWindows.png)

The results should appear as

![alt tag](https://github.com/Estafet-LTD/estafet-microservices-scrum/blob/master/CucumberTestResultsOnWindows.png)

#### Executing via command line
```
mvn clean test
```
