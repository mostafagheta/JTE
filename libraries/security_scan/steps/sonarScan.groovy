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

    echo "Running SonarQube Scanner on ${env.NODE_NAME} for ${projectKey} using credential ${credentialId}..."
    withCredentials([string(credentialsId: credentialId, variable: 'SONAR_TOKEN')]) {
        withSonarQubeEnv(sonarServer) {
            sh """
                chmod +x ./mvnw || true
                ./mvnw -B org.sonarsource.scanner.maven:sonar-maven-plugin:5.1.0.4751:sonar \
                  -DskipTests \
                  -Dcheckstyle.skip=true \
                  -Dsonar.projectKey=${projectKey} \
                  -Dsonar.token=\$SONAR_TOKEN
            """
        }
    }
}
