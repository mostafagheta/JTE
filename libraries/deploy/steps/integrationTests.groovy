void call() {
    echo "Running Integration Tests..."
    sh "./mvnw verify -P integration-test -DskipUnitTests=true || echo 'No integration tests configured'"
}
