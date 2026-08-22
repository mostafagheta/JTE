void call() {
    echo "Generating and Publishing SBOM..."
    def image = env.APP_IMAGE ?: resolveAppImage()
    sh "syft ${image} -o spdx-json=sbom.json"
    archiveArtifacts artifacts: 'sbom.json'
}
