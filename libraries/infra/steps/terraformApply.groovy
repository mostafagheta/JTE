def call() {

    def action = params.ACTION?.toLowerCase()

    echo "========================================"
    echo "Terraform ${action.toUpperCase()}"
    echo "========================================"

    sh """
        set -e

        if [ ! -f tfplan ]; then
            echo "ERROR: Terraform plan file tfplan does not exist."
            exit 1
        fi

        terraform apply \
            -input=false \
            tfplan
    """

    echo "Terraform ${action} completed successfully."
}