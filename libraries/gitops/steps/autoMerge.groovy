void call(Map mergeConfig) {
    def targetBranch = mergeConfig.targetBranch
    def sourceBranch = env.BRANCH_NAME
    def credentialId = "40064b7c-67f3-4b2c-8d3d-57d801eb56c3"

    try {
        if (config.git_credential) {
            credentialId = config.git_credential.toString()
        }
    } catch (Exception ignored) {}

    echo "Automated Merge: Merging ${sourceBranch} into ${targetBranch} using credential ${credentialId}"

    withCredentials([usernamePassword(credentialsId: credentialId, usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
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

            set +x
            case "\$GIT_PASSWORD" in
              ghp_*|github_pat_*|gho_*|ghu_*|ghs_*)
                ;;
              *)
                echo "ERROR: GitHub no longer accepts an account password for git push (HTTPS)."
                echo "Keep the Jenkins credential as Username with password."
                echo "Username: your GitHub username (mostafagheta)."
                echo "Password: a Personal Access Token (not your GitHub login password)."
                echo "Create one at https://github.com/settings/tokens"
                echo "Classic token: enable the repo scope."
                echo "Fine-grained: repo mostafagheta/final_project, Contents Read and write."
                echo "The token starts with ghp_ or github_pat_ — paste that into the Password field."
                exit 1
                ;;
            esac

            AUTH=\$(printf 'x-access-token:%s' "\$GIT_PASSWORD" | base64 | tr -d '\\n')
            git -c http.extraHeader="Authorization: Basic \$AUTH" push origin "HEAD:refs/heads/${targetBranch}"

            git checkout "${sourceBranch}" || git checkout -B "${sourceBranch}" "origin/${sourceBranch}"
        """
    }
}
