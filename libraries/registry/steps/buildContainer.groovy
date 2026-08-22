void call() {
    echo "Building Container Image..."
    def registry = config.ecr_registry
    def repo = config.ecr_repo
    def tag = env.GIT_COMMIT ?: "latest"
    sh "docker build -t ${registry}/${repo}:${tag} ."
}
