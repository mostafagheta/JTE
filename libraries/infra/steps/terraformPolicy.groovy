def call() {
    echo "========================================"
    echo "Terraform Policy / Compliance"
    echo "========================================"

    sh '''
        set -e

        echo "Running Checkov..."

        checkov \
            -d . \
            --framework terraform \
            --compact \
            --quiet
    '''

    echo "Terraform policy/compliance check completed successfully."
}