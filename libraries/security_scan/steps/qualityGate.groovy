void call() {

    def projectKey = "spring-petclinic-main"

    echo "Checking SonarQube Quality Gate for ${projectKey}..."

    timeout(time: 1, unit: 'HOURS') {

        def qg = waitForQualityGate()

        if (qg.status != 'OK') {
            error "Pipeline aborted due to Quality Gate failure: ${qg.status}"
        }
    }

    echo "Quality Gate passed."

    withSonarQubeEnv('SonarQube') {

        def coverage = sh(
            script: """
                curl -s \
                "\$SONAR_HOST_URL/api/measures/component?component=${projectKey}&metricKeys=coverage" \
                | jq -r '.component.measures[0].value'
            """,
            returnStdout: true
        ).trim()

        if (!coverage || coverage == 'null') {
            error "Unable to retrieve code coverage for ${projectKey}"
        }

        def coverageValue = coverage.toBigDecimal()

        echo "Code coverage: ${coverageValue}%"
        echo "Required coverage: 80%"

        if (coverageValue < 80) {
            error "Pipeline aborted: code coverage ${coverageValue}% is below the required 80%"
        }

        echo "Code coverage check passed."
    }
}