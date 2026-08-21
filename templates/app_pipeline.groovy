pipeline {
    agent {
        label 'ec2-static'
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    
    stages {
        stage('Validate Entry Criteria') {
            steps {
                script {
                    validateEntryCriteria()
                }
            }
        }
        
        stage('Check Versioning') {
            steps {
                script {
                    def hasChanges = checkS3Version(
                        bucket: pipelineConfig.s3_bucket,
                        file: pipelineConfig.s3_version_file
                    )
                    if (!hasChanges) {
                        currentBuild.result = 'SUCCESS'
                        echo "No version changes detected. Skipping pipeline."
                        env.SKIP_PIPELINE = 'true'
                    } else {
                        echo "Version change detected. Updating S3 version bucket immediately."
                        updateS3Version()
                    }
                }
            }
        }
        
        stage('Build & Test') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    buildApp()
                    unitTests()
                }
            }
        }
        
        stage('Code Analysis') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    sonarScan()
                    qualityGate()
                }
            }
        }
        
        stage('Security Scanning') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    dependencyScan()
                }
            }
        }
        
        stage('Package Artifact') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    packageArtifact()
                }
            }
        }
        
        stage('Containerize & Scan') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    buildContainer()
                    imageVulnerabilityScan()
                }
            }
        }
        
        stage('Publish Artifacts') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    pushToECR()
                    generateSBOM()
                }
            }
        }
        
        // GitOps syncing is handled automatically by Keel mapping directly to ECR image tag updates
        
        stage('Integration & Performance') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    integrationTests()
                    performanceTests()
                }
            }
        }
        
        stage('Infrastructure Compliance') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    validateInfrastructure()
                }
            }
        }
        
        stage('Production Approval') {
            when {
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    validateExitCriteria()
                    timeout(time: 24, unit: 'HOURS') {
                        input message: 'READY FOR PROD: Approve production deployment?', submitter: 'admin-group'
                    }
                    triggerProdDeploy()
                }
            }
        }
    }
}
