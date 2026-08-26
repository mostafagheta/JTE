def call() {
    echo "========================================"
    echo "Ansible Security Scan"
    echo "========================================"

    sh '''
        set -e
        cd ansible
        trivy config \
            --severity HIGH,CRITICAL \
            --exit-code 0 \
            .
    '''

    echo "Ansible security scan completed."
}