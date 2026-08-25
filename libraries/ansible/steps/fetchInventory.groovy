// fetchInventory/fetchInventory.groovy
void call(Map config = [:]) {
    copyArtifacts(
        projectName: config.projectName,
        selector: config.get('selector', lastSuccessful()),
        filter: config.get('filter', 'inventory.ini, modules/eks/cluster-autoscaler.yaml'),
        fingerprintArtifacts: true
    )
}