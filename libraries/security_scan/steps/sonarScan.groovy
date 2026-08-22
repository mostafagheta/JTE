void call() {
    def projectKey = "spring-petclinic-main"
    def credentialId = "spring-petclinic-main"
    def sonarServer = "SonarQube"

    try {
        if (config.sonar_project_key) {
            projectKey = config.sonar_project_key.toString()
        }
        if (config.sonar_token_credential) {
            credentialId = config.sonar_token_credential.toString()
        }
        if (config.sonar_server) {
            sonarServer = config.sonar_server.toString()
        }
    } catch (Exception ignored) {}

    echo "Running SonarQube Scanner for ${projectKey} using credential ${credentialId}..."
    withCredentials([string(credentialsId: credentialId, variable: 'SONAR_TOKEN')]) {
        withSonarQubeEnv(sonarServer) {
            sh """
                ./mvnw sonar:sonar \
                  -Dsonar.projectKey=${projectKey} \
                  -Dsonar.token=\$SONAR_TOKEN
            """
        }
    }
}
