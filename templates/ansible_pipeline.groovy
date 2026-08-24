pipeline {
    agent {
        label 'ec2-static'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {

        stage('Ansible Dependencies') {
            steps {
                ansibleDependencies()
            }
        }

        stage('Ansible Syntax') {
            steps {
                ansibleSyntax()
            }
        }

        stage('Ansible Lint') {
            steps {
                ansibleLint()
            }
        }

        stage('Ansible Security Scan') {
            steps {
                ansibleSecurity()
            }
        }

        stage('EKS Connectivity') {
            steps {
                eksConnectivity()
            }
        }

        stage('Ansible Check') {
            steps {
                ansibleCheck()
            }
        }

        stage('Approval') {
            steps {
                ansibleApproval()
            }
        }

        stage('Ansible Apply') {
            steps {
                ansibleApply()
            }
        }

        stage('Verify EKS Add-ons') {
            steps {
                ansibleVerify()
            }
        }
    }
}