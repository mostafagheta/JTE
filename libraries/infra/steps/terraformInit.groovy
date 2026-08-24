def call() {
    echo "========================================"
    echo "Terraform Init"
    echo "========================================"

    sh '''
        set -e

        echo "Terraform version:"
        terraform version

        echo "Initializing Terraform..."

        terraform init \
            -input=false

        echo "Terraform initialization completed successfully."
    '''
}