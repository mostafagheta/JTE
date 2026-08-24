void call(String sonar_project_key) {
    echo "Checking SonarQube Quality Gate for: ${sonar_project_key}"

    timeout(time: 1, unit: 'HOURS') {
        def qg = waitForQualityGate()

        if (qg.status != 'OK') {
            error "Pipeline aborted due to Quality Gate failure: ${qg.status}"
        }
    }

    withSonarQubeEnv('SonarQube') {
        def coverage = sh(
            script: """
                curl -s \
                "\$SONAR_HOST_URL/api/measures/component?component=${sonar_project_key}&metricKeys=coverage" \
                | jq -r '.component.measures[0].value'
            """,
            returnStdout: true
        ).trim()

        if (!coverage || coverage == 'null') {
            error "Unable to retrieve code coverage from SonarQube"
        }

        def coverageValue = coverage.toBigDecimal()

        echo "Code coverage: ${coverageValue}%"

        if (coverageValue < 80) {
            error "Code coverage ${coverageValue}% is below the required 80%"
        }

        echo "Code coverage passed: ${coverageValue}%"
    }
}