void call() {
    echo "Checking JaCoCo report..."

    sh '''
        ls -lah target/site/jacoco/ || true
        test -f target/site/jacoco/jacoco.xml
    '''

    echo "JaCoCo report found successfully."
}