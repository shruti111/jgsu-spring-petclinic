pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/shruti111/jgsu-spring-petclinic.git'
            }
        }
        stage('Build') {
            steps {
                sh './mvnw clean package'
                // Run Maven on a Unix agent.
                // sh &quot;mvn -Dmaven.test.failure.ignore=true clean package&quot;

                // To run Maven on a Windows agent, use
                // bat &quot;mvn -Dmaven.test.failure.ignore=true clean package&quot;
            }

            post {
              //  If Maven was able to run the tests, even if  some of the test
              //  failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml';
                    archiveArtifacts 'target/*.jar';
                }
            }
        }
    }
}