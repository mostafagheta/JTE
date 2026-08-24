def call() {
    echo "========================================"
    echo "Ansible Security Scan"
    echo "========================================"

    sh '''
        set -e

        trivy config \
            --severity HIGH,CRITICAL \
            --exit-code 1 \
            .
    '''

    echo "Ansible security scan completed."
}