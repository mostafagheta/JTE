pipeline {
    agent {
        label 'ec2-static'
    }

    parameters {
        choice(
            name: 'ACTION',
            choices: ['apply', 'destroy'],
            description: 'Select Terraform action'
        )
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {

        stage('Terraform Init') {
            steps {
                terraformInit()
            }
        }

        stage('Terraform Validate') {
            steps {
                terraformValidate()
            }
        }

        stage('Terraform Lint') {
            steps {
                terraformLint()
            }
        }

        stage('Terraform Security Scan') {
            steps {
                terraformSecurity()
            }
        }

        stage('Terraform Plan') {
            steps {
                terraformPlan()
            }
        }

        stage('Policy / Compliance Check') {
            steps {
                terraformPolicy()
            }
        }

        stage('Approval') {
            steps {
                terraformApproval()
            }
        }

        stage('Terraform Execute') {
            steps {
                terraformApply()
            }
        }
          stage('Archive Artifacts') {
         steps {
            archiveInventory()
         }
    }
}