def call() {
    echo "========================================"
    echo "Terraform Validate"
    echo "========================================"

    sh '''
        set -e

        echo "Validating Terraform configuration..."

        terraform validate

        echo "Terraform validation completed successfully."
    '''
}