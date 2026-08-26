void call(Map config = [:]) {
    // Import Jenkins model if needed
    import jenkins.model.Jenkins
    
    // Get the last successful build of the project
    def job = Jenkins.instance.getItem(config.projectName)
    def lastSuccessfulBuild = job?.lastSuccessfulBuild
    
    copyArtifacts(
        projectName: config.projectName,
        selector: config.get('selector', lastSuccessfulBuild),  // Use property, not method
        filter: config.get('filter', 'inventory.ini, cluster-autoscaler.yaml'),
        fingerprintArtifacts: true
    )
}