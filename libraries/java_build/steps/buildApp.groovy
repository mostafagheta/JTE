void call() {
    echo "Building application with Maven..."
    sh """
        rm -f sbom.json current_s3_version.json HAS_CHANGES
        ./mvnw clean compile -DskipTests
    """
}
