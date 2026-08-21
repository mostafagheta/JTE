void call() {
    def branchName = env.BRANCH_NAME ?: 'dev' // fallback if not multibranch
    echo "Evaluating Target ECR Repository for branch: ${branchName}"
    
    def registry = pipelineConfig.ecr_registry
    def baseRepo = pipelineConfig.ecr_repo
    def region = pipelineConfig.aws_region
    def baseTag = env.GIT_COMMIT ?: "latest"

    // Map branch to target environment suffix
    def envSuffix = ""
    if (branchName == 'dev') {
        envSuffix = "dev"
    } else if (branchName == 'test') {
        envSuffix = "test"
    } else if (branchName == 'main' || branchName == 'master' || branchName == 'prod') {
        envSuffix = "prod"
    } else {
        echo "Branch '${branchName}' does not have a mapped ECR repository. Skipping push."
        return
    }

    // e.g. spring-petclinic-dev
    def targetRepo = "${baseRepo}-${envSuffix}"
    echo "Pushing Container Image to ECR Repo: ${targetRepo}..."

    sh """
        aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${registry}
        
        # Tag the base built image to the target repository
        docker tag ${registry}/${baseRepo}:${baseTag} ${registry}/${targetRepo}:${baseTag}

        # Push exactly to the mapped repo
        docker push ${registry}/${targetRepo}:${baseTag}
    """
}
