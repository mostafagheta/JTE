void call() {
    echo "Generating and Publishing SBOM..."
    def registry = config.ecr_registry
    def repo = config.ecr_repo
    def tag = env.GIT_COMMIT ?: "latest"
    sh "syft ${registry}/${repo}:${tag} -o spdx-json=sbom.json"
    archiveArtifacts artifacts: 'sbom.json'
}
