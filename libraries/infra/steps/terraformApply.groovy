void call() {
    echo "Applying Terraform..."
    sh "terraform apply tfplan"
}
