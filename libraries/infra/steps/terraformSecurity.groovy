def call() {
    echo "========================================"
    echo "Terraform Security Scan"
    echo "========================================"

    sh '''
        set -e

        echo "Running Trivy IaC security scan..."

        trivy config \
            --severity HIGH,CRITICAL \
            --exit-code 1 \
            .
    '''

    echo "Terraform security scan completed successfully."
}