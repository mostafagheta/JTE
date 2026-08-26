void call(Map config = [:]) {
    // Get build number from config or use default
    def buildNumber = config.buildNumber ?: 'last-successful'
    def projectName = config.projectName ?: 'infra'
    
    copyArtifacts(
        projectName: projectName,
        selector: specific(buildNumber),
        filter: config.filter ?: 'inventory.ini, cluster-autoscaler.yaml',
        fingerprintArtifacts: true
    )
}