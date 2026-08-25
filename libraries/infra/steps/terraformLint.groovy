def call() {
    echo "========================================"
    echo "Terraform Lint"
    echo "========================================"

    sh '''
        set -e

        echo "Running TFLint..."

        tflint --init

        tflint --format compact --fail-on-error
    '''

    echo "Terraform lint completed successfully."
}