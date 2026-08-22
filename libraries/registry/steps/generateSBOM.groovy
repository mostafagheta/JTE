void call() {
    echo "Generating and Publishing SBOM..."
    def image = env.APP_IMAGE ?: resolveAppImage()
    sh """
        if command -v syft >/dev/null 2>&1; then
          syft ${image} -o spdx-json=sbom.json
        else
          echo "syft is not installed on this agent; generating SPDX SBOM with trivy."
          trivy image --format spdx-json --output sbom.json ${image}
        fi
    """
    archiveArtifacts artifacts: 'sbom.json'
}
