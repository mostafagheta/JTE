void call() {

    echo "Running unit tests with JaCoCo coverage..."

    sh "./mvnw test"

    echo "Generating JaCoCo report..."

    sh "./mvnw jacoco:report"

}