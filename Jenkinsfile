
pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven3.8.5"
    }

    stages {
        stage('Clean') {
            steps {
                // Get some code from a GitHub repository
                 git 'https://github.com/mariamaLaye/maven-1.git'

                // To run Maven on a Windows agent, use
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }
        stage('Compile') {
            steps {
                 // To run Maven on a Windows agent, use
                 git 'https://github.com/mariamaLaye/maven-1.git'
                
                bat "mvn -Dmaven.test.failure.ignore=true compile package"
            }
        }
    }
}



