void call() {
    echo "Building Container Image..."
    def registry = pipelineConfig.ecr_registry
    def repo = pipelineConfig.ecr_repo
    def tag = env.GIT_COMMIT ?: "latest"
    sh "docker build -t ${registry}/${repo}:${tag} ."
}
