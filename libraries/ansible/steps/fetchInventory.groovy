stage('Fetch Infrastructure Inventory') {
    steps {
        script {
            step([
                $class: 'CopyArtifact',
                projectName: 'infra',
                selector: [$class: 'LastSuccessfulBuildSelector'],
                filter: 'inventory.ini, cluster-autoscaler.yaml',
                fingerprintArtifacts: true
            ])
        }
    }
}