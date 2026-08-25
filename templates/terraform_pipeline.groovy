// 1. Set agent node
node('ec2-static') {

    // 2. Set options / parameters directly if needed or run stages
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