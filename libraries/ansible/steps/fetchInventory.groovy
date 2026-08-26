def call() {
    // Use copyArtifacts to fetch the inventory files
    copyArtifacts(
        projectName: 'infra',
        selector: 'last-successful',
        filter: 'inventory.ini, cluster-autoscaler.yaml',
        fingerprintArtifacts: true
    )
    
    echo "Inventory files fetched successfully from 'infra' job"
}