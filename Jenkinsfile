pipeline {
  agent any

  parameters {
    string(name: 'SUITE_NAME',
           defaultValue: 'smoke.xml',
           description: 'Which TestNG suite to run')
  }

  environment {
    COSMOS_CONN = credentials('cosmos-conn-string')
  }

  stages {
    stage('Checkout') {
      steps {
        git(
          url: 'https://github.com/ManikantaSpark/final-demo-spring-boot-ci.git',
          credentialsId: 'github-pat-ci',
          branch: 'main'
        )
      }
    }

    stage('Run TestNG Suite') {
      steps {
        sh "mvn clean test -DsuiteXmlFile=src/test/resources/suites/\${params.SUITE_NAME}"
      }
    }

    stage('Publish to Jenkins') {
      steps {
        junit 'target/surefire-reports/*.xml'
      }
    }

    stage('Push to CosmosDB') {
      steps {
        sh """
          python3 ci/push_results_to_cosmos.py \
            --conn \"\$COSMOS_CONN\" \
            --suite \${params.SUITE_NAME}
        """
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/surefire-reports/*.html', fingerprint: true
    }
  }
}
