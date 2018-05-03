node('maven') {

	def project = "test"

	stage("checkout") {
		git branch: "master", url: "https://github.com/Estafet-LTD/estafet-microservices-scrum-qa"
	}

	stage("execute acceptance tests") {
		sh "mvn clean install"
		junit "**/target/surefire-reports/*.xml"	
	}

}

