void call() {
    echo "Restoring Maven build output..."

    unstash 'maven-build-output'

    echo "Checking transferred files..."

    sh '''
        echo "=== Classes ==="
        ls -lah target/classes/ | head

        echo "=== JaCoCo ==="
        ls -lah target/site/jacoco/

        test -f target/site/jacoco/jacoco.xml
    '''

    echo "Maven build output restored successfully."
}