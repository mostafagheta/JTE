void call() {
    echo "Packaging Application Artifact..."
    sh "./mvnw package -DskipTests"
    // Save artifact for immutability
    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
}
