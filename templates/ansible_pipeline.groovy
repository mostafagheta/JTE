pipeline {
    agent {
        label 'ec2-static'
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    
    stages {
        stage('Ansible Lint') {
            steps {
                script {
                    ansibleLint()
                }
            }
        }
        
        stage('Ansible Dry Run') {
            steps {
                script {
                    ansiblePlaybook(check: true)
                }
            }
        }
        
        stage('Approval') {
            steps {
                script {
                    timeout(time: 1, unit: 'HOURS') {
                        input message: 'Approve Ansible Playbook Execution?', submitter: 'platform-admins'
                    }
                }
            }
        }
        
        stage('Ansible Apply') {
            steps {
                script {
                    ansiblePlaybook(check: false)
                }
            }
        }
    }
}
