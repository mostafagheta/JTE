pipeline {
    agent {
        label 'ec2-static'
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    
    stages {
        
        stage('Check Versioning') {
            steps {
                script {
                    def hasChanges = checkS3Version()
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
                branch 'dev'
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    buildApp()
                    unitTests()
                    sh '''
                    echo "Checking JaCoCo report..."
                    ls -lah target/site/jacoco/ || true
                    test -f target/site/jacoco/jacoco.xml
                    '''

                    stash name: 'maven-build-output',
                    includes: '''
                        target/classes/**,
                        target/test-classes/**,
                        target/site/jacoco/**,
                        target/surefire-reports/**
                        ''',
                    useDefaultExcludes: false
                }
            }
        }
        stage('Code Analysis') {
            agent {
                label 'master'
            }
            when {
                branch 'dev'
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
             unstash 'maven-build-output'
                sh '''
                    echo "Checking transferred files..."

                    echo "=== Classes ==="
                    ls -lah target/classes/ | head

                    echo "=== JaCoCo ==="
                    ls -lah target/site/jacoco/

                    test -f target/site/jacoco/jacoco.xml
                    '''    
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
                branch 'dev'
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    packageArtifact()
                }
            }
        }
        
        stage('Containerize & Scan (Dev Only)') {
            when {
                branch 'dev'
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
        stage('Promote & Merge Dev -> Test') {
            when {
                branch 'dev'
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    echo "Dev Pipeline successful! Promoting code to test branch via git merge..."
                    autoMerge(targetBranch: 'test')
                }
            }
        }
        
        stage('Approval & Merge Test -> Prod') {
            when {
                branch 'test'
                environment name: 'SKIP_PIPELINE', value: ''
            }
            steps {
                script {
                    validateExitCriteria()
                    timeout(time: 24, unit: 'HOURS') {
                        input message: 'READY FOR PROD: Approve merging these changes into the prod branch?', submitter: 'admin-group'
                    }
                    echo "Approval granted. Promoting code to prod branch via git merge..."
                    autoMerge(targetBranch: 'prod')
                }
            }
        }
        
    }
}
