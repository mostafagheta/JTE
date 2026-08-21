void call() {
    echo "Running Dependency Scanning..."
    sh "./mvnw org.owasp:dependency-check-maven:check"
}
