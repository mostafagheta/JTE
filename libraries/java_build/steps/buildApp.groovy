void call() {
    echo "Building application with Maven..."
    sh "./mvnw clean compile -DskipTests"
}
