void call(Map mergeConfig) {
    def targetBranch = mergeConfig.targetBranch
    def sourceBranch = env.BRANCH_NAME

    echo "Automated Merge: Merging ${sourceBranch} into ${targetBranch}"

    sh """
        git config user.email "jenkins-auto-merger@atos.net"
        git config user.name "Jenkins Auto Merger"

        git fetch origin "+refs/heads/${sourceBranch}:refs/remotes/origin/${sourceBranch}" || git fetch origin
        git fetch origin "+refs/heads/${targetBranch}:refs/remotes/origin/${targetBranch}" || true

        if git rev-parse --verify "origin/${targetBranch}" >/dev/null 2>&1; then
          git checkout -B "${targetBranch}" "origin/${targetBranch}"
          git merge "origin/${sourceBranch}" -m "chore(auto-promote): Merge ${sourceBranch} into ${targetBranch} triggered by Jenkins build ${env.BUILD_NUMBER}"
        else
          echo "Branch '${targetBranch}' does not exist on origin; creating it from ${sourceBranch}"
          git checkout -B "${targetBranch}" "origin/${sourceBranch}"
        fi

        git push origin "${targetBranch}"
        git checkout "${sourceBranch}" || git checkout -B "${sourceBranch}" "origin/${sourceBranch}"
    """
}
