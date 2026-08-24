void call() {
    echo "Stashing Maven build output..."

    stash(
        name: 'maven-build-output',
        includes: '''
            target/classes/**,
            target/test-classes/**,
            target/site/jacoco/**,
            target/surefire-reports/**
        ''',
        useDefaultExcludes: false
    )

    echo "Maven build output stashed successfully."
}