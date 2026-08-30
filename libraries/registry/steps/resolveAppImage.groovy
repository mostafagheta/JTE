String call() {
    def tag = (env.GIT_COMMIT ?: "latest").toString()
    def registry
    def repo

    try {
        if (config.ecr_registry) {
            registry = config.ecr_registry.toString()
        }
        if (config.ecr_repo) {
            repo = config.ecr_repo.toString()
        }
    } catch (Exception ignored) {}

    try {
        if (!registry && ecr_registry) {
            registry = ecr_registry.toString()
        }
        if (!repo && ecr_repo) {
            repo = ecr_repo.toString()
        }
    } catch (Exception ignored) {}

    if (!registry) {
        registry = "130299714330.dkr.ecr.eu-central-1.amazonaws.com"
    }
    if (!repo) {
        repo = "petclinic"
    }

    return "${registry}/${repo}:${tag}"
}
