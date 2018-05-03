node('maven') {

	def project = "test"

	stage("checkout") {
		git branch: "master", url: "https://github.com/Estafet-LTD/estafet-microservices-scrum-qa"
	}

	stage("execute acceptance tests") {
		withMaven ( maven: 'M3', mavenSettingsConfig: 'my-maven-settings', mavenLocalRepo: '.repository') {
      		sh "mvn clean install"
    	}
	}

}

