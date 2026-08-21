void call() {
    def branchName = env.BRANCH_NAME ?: 'dev'
    echo "Evaluating Target ECR Repository for branch: ${branchName}"
    
    def registry = pipelineConfig.ecr_registry
    def baseRepo = pipelineConfig.ecr_repo
    def region = pipelineConfig.aws_region
    def baseTag = env.GIT_COMMIT ?: "latest"

    def targetSuffix = ""
    def sourceSuffix = ""

    if (branchName == 'dev') {
        targetSuffix = "dev"
    } else if (branchName == 'test') {
        targetSuffix = "test"
        sourceSuffix = "dev"
    } else if (branchName == 'main' || branchName == 'master' || branchName == 'prod') {
        targetSuffix = "prod"
        sourceSuffix = "test"
    } else {
        echo "Branch '${branchName}' does not have a mapped ECR repository. Skipping push."
        return
    }

    def targetRepo = "${baseRepo}-${targetSuffix}"
    echo "Pushing Container Image to ECR Repo: ${targetRepo}..."

    sh "aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${registry}"

    if (branchName == 'dev') {
        // Image was built locally in Containerize stage, just tag and push
        sh """
            docker tag ${registry}/${baseRepo}:${baseTag} ${registry}/${targetRepo}:${baseTag}
            docker tag ${registry}/${baseRepo}:${baseTag} ${registry}/${targetRepo}:latest
            
            docker push ${registry}/${targetRepo}:${baseTag}
            docker push ${registry}/${targetRepo}:latest
        """
    } else {
        // IMAGE PROMOTION: Pull from previous tier, re-tag, push
        def sourceRepo = "${baseRepo}-${sourceSuffix}"
        echo "PROMOTING IMAGE: Pulling ${sourceRepo}:latest to deploy to ${targetRepo}"
        sh """
            docker pull ${registry}/${sourceRepo}:latest || echo "Warning: Couldn't pull latest"
            
            docker tag ${registry}/${sourceRepo}:latest ${registry}/${targetRepo}:${baseTag}
            docker tag ${registry}/${sourceRepo}:latest ${registry}/${targetRepo}:latest
            
            docker push ${registry}/${targetRepo}:${baseTag}
            docker push ${registry}/${targetRepo}:latest
        """
    }
}
