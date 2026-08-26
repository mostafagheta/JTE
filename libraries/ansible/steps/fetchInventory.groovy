void call(Map config = [:]) {
    // Accept selector from pipeline or use default
    def selector = config.get('selector', lastBuild())
    def projectName = config.get('projectName', 'infra')
    def filter = config.get('filter', 'inventory.ini, cluster-autoscaler.yaml')
    
    copyArtifacts(
        projectName: projectName,
        selector: selector,
        filter: filter,
        fingerprintArtifacts: true
    )
}