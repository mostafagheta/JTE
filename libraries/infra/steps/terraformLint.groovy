def call() {
    echo "========================================"
    echo "Terraform Lint"
    echo "========================================"

    sh '''
        set -e

        echo "Running TFLint..."

        tflint --init

        tflint --format compact
    '''

    echo "Terraform lint completed successfully."
}