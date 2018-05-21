node('maven') {

	def project = "test"

	stage("checkout") {
		git branch: "master", url: "https://github.com/Estafet-LTD/estafet-microservices-scrum-qa"
	}

	stage("unit tests") {
		withEnv( [ 	"BASIC_UI_URI=http://${microservice}.${project}.svc:8080",
								"TASK_API_JDBC_URL=jdbc:postgresql://postgresql.${project}.svc:5432/${microservice}", 
								"TASK_API_DB_USER=postgres", 
								"TASK_API_DB_PASSWORD=welcome1",
								"TASK_API_SERVICE_URI=http://${microservice}.${project}.svc:8080",
								"STORY_API_JDBC_URL=jdbc:postgresql://postgresql.${project}.svc:5432/${microservice}", 
								"STORY_API_DB_USER=postgres", 
								"STORY_API_DB_PASSWORD=welcome1",
								"STORY_API_SERVICE_URI=http://${microservice}.${project}.svc:8080",
								"SPRINT_BURNDOWN_JDBC_URL=jdbc:postgresql://postgresql.${project}.svc:5432/${microservice}", 
								"SPRINT_BURNDOWN_DB_USER=postgres", 
								"SPRINT_BURNDOWN_DB_PASSWORD=welcome1",
								"SPRINT_BURNDOWN_SERVICE_URI=http://${microservice}.${project}.svc:8080",
								"SPRINT_API_JDBC_URL=jdbc:postgresql://postgresql.${project}.svc:5432/${microservice}", 
								"SPRINT_API_DB_USER=postgres", 
								"SPRINT_API_DB_PASSWORD=welcome1",
								"SPRINT_API_SERVICE_URI=http://${microservice}.${project}.svc:8080",
								"PROJECT_BURNDOWN_REPOSITORY_JDBC_URL=jdbc:postgresql://postgresql.${project}.svc:5432/${microservice}", 
								"PROJECT_BURNDOWN_REPOSITORY_DB_USER=postgres", 
								"PROJECT_BURNDOWN_REPOSITORY_DB_PASSWORD=welcome1",
								"PROJECT_BURNDOWN_SERVICE_URI=http://${microservice}.${project}.svc:8080",
								"PROJECT_API_JDBC_URL=jdbc:postgresql://postgresql.${project}.svc:5432/${microservice}", 
								"PROJECT_API_DB_USER=postgres", 
								"PROJECT_API_DB_PASSWORD=welcome1",
								"PROJECT_API_SERVICE_URI=http://${microservice}.${project}.svc:8080" ]) {
			withMaven(mavenSettingsConfig: 'microservices-scrum') {
		  	sh "mvn clean test"
		  } 
		}
	}

}

