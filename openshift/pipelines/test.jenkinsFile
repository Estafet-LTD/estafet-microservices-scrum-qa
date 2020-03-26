node('maven') {

	properties([
	  parameters([
	     string(name: 'GITHUB'), string(name: 'PRODUCT'), string(name: 'REPO'),
	  ])
	])

	def project = "${params.PRODUCT}-test"

	stage("checkout") {
		git branch: "master", url: "https://github.com/${params.GITHUB}/${params.REPO}-qa"
	}

	stage("reset test flags for ${project}") {
		sh "oc label namespace ${project} test-passed=false --overwrite=true"	
	}

	stage("cucumber tests") {
		withMaven(mavenSettingsConfig: 'microservices-scrum') {
			try {
				sh "mvn clean test"	
			} finally {
				cucumber buildStatus: 'UNSTABLE', fileIncludePattern: '**/*cucumber-report.json',  trendsLimit: 10
			}
		} 
	}
	
	stage("flag this environment") {
		if (currentBuild.currentResult == 'SUCCESS') {
			println "The tests passed successfully"
			sh "oc label namespace ${project} test-passed=true --overwrite=true"	
		}
	}
	
}

