void call() {
    echo "Running SonarQube Scanner..."
    withSonarQubeEnv('SonarQube') {
        sh "./mvnw sonar:sonar -Dsonar.projectKey=${config.sonar_project_key}"
    }
}
