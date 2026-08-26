def call(Map config = [:]) {
    def projectName = config.projectName ?: 'infra'
    def buildNumber  = config.buildNumber ?: 'last-successful'
    def filter       = config.filter ?: 'inventory.ini, cluster-autoscaler.yaml'

    copyArtifacts(
        projectName: projectName,
        selector: buildNumber == 'last-successful' ? lastSuccessful() : specific(buildNumber),
        filter: filter,
        fingerprintArtifacts: true
    )

    echo "Inventory files fetched successfully from '${projectName}' job"
}