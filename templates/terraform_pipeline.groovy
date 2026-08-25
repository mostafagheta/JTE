// 1. Set agent node
node('ec2-static') {

    stage('Checkout Terraform Code') {
        git branch: 'main',
            url: 'https://github.com/mostafagheta/Infra_repo.git'
      }
    stage('Terraform Init') {
        terraformInit()
    }

    stage('Terraform Validate') {
        terraformValidate()
    }

    stage('Terraform Lint') {
        terraformLint()
    }

    stage('Terraform Security Scan') {
        terraformSecurity()
    }

    stage('Terraform Plan') {
        terraformPlan()
    }

    stage('Policy / Compliance Check') {
        terraformPolicy()
    }

    stage('Approval') {
        terraformApproval()
    }

    stage('Terraform Execute') {
        terraformApply()
    }

    stage('Archive Artifacts') {
        archiveInventory()
    }
}