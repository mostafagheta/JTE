void call(Map args) {
    def envName = args.env
    echo "Waiting for Keel/GitOps to finish deployment to environment: ${envName}..."
    // Placeholder polling logic
    sh "sleep 30"
}
