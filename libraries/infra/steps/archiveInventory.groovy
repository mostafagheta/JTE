// archiveInventory/archiveInventory.groovy
void call(Map config = [:]) {
    def artifactPaths = config.get('paths', 'inventory.ini, modules/eks/cluster-autoscaler.yaml')
    def fingerprint = config.get('fingerprint', true)

    archiveArtifacts artifacts: artifactPaths, fingerprint: fingerprint
}