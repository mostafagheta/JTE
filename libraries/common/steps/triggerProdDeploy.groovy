void call() {
    echo "Triggering Production Deployment..."
    // Wait for Keel/GitOps to update prod env, or push to prod branch
    sh "echo 'Prod deployment triggered!'"
}
