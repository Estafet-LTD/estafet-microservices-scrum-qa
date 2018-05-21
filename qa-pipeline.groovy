node('maven') {

	stage("checkout") {
		git branch: "master", url: "https://github.com/Estafet-LTD/estafet-microservices-scrum-qa"
	}

	stage("unit tests") {
		withEnv( [ 	"BASIC_UI_URI=http://basic-ui.test.svc:8080",
								"TASK_API_JDBC_URL=jdbc:postgresql://postgresql.test.svc:5432/task-api", 
								"TASK_API_DB_USER=postgres", 
								"TASK_API_DB_PASSWORD=welcome1",
								"TASK_API_SERVICE_URI=http://task-api.test.svc:8080",
								"STORY_API_JDBC_URL=jdbc:postgresql://postgresql.test.svc:5432/story-api", 
								"STORY_API_DB_USER=postgres", 
								"STORY_API_DB_PASSWORD=welcome1",
								"STORY_API_SERVICE_URI=http://story-api.test.svc:8080",
								"SPRINT_BURNDOWN_JDBC_URL=jdbc:postgresql://postgresql.test.svc:5432/sprint-burndown", 
								"SPRINT_BURNDOWN_DB_USER=postgres", 
								"SPRINT_BURNDOWN_DB_PASSWORD=welcome1",
								"SPRINT_BURNDOWN_SERVICE_URI=http://sprint-burndown.test.svc:8080",
								"SPRINT_API_JDBC_URL=jdbc:postgresql://postgresql.test.svc:5432/sprint-api", 
								"SPRINT_API_DB_USER=postgres", 
								"SPRINT_API_DB_PASSWORD=welcome1",
								"SPRINT_API_SERVICE_URI=http://sprint-api.test.svc:8080",
								"PROJECT_BURNDOWN_REPOSITORY_JDBC_URL=jdbc:postgresql://postgresql.test.svc:5432/project-burndown", 
								"PROJECT_BURNDOWN_REPOSITORY_DB_USER=postgres", 
								"PROJECT_BURNDOWN_REPOSITORY_DB_PASSWORD=welcome1",
								"PROJECT_BURNDOWN_SERVICE_URI=http://project-burndown.test.svc:8080",
								"PROJECT_API_JDBC_URL=jdbc:postgresql://postgresql.test.svc:5432/project-api", 
								"PROJECT_API_DB_USER=postgres", 
								"PROJECT_API_DB_PASSWORD=welcome1",
								"PROJECT_API_SERVICE_URI=http://project-api.test.svc:8080" ]) {
			withMaven(mavenSettingsConfig: 'microservices-scrum') {
		  	sh "mvn clean test"
		  } 
		}
	}

}

