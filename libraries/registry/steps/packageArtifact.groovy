void call() {
    echo "Packaging Application Artifact..."
    sh "./mvnw package -DskipTests"
    // Do not archive the Spring Boot fat JAR to the controller. Copying that
    // file over the Jenkins remoting channel has dropped the agent connection.
    // The container image published to ECR is the immutable runtime artifact.
    archiveArtifacts artifacts: 'target/classes/META-INF/sbom/application.cdx.json', allowEmptyArchive: true
}
