void call() {
    echo "Planning Terraform..."
    sh "terraform plan -out=tfplan"
}
