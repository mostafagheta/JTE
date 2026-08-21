pipeline {
    agent {
        label 'ec2-static'
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    
    stages {
        stage('Terraform Init & Validate') {
            steps {
                script {
                    terraformInit()
                    terraformValidate()
                }
            }
        }
        
        stage('Terraform Plan') {
            steps {
                script {
                    terraformPlan()
                }
            }
        }
        
        stage('Approval') {
            steps {
                script {
                    timeout(time: 1, unit: 'HOURS') {
                        input message: 'Approve Terraform Apply?', submitter: 'platform-admins'
                    }
                }
            }
        }
        
        stage('Terraform Apply') {
            steps {
                script {
                    terraformApply()
                }
            }
        }
    }
}
