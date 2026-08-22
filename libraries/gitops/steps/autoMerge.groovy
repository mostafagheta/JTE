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

            ASKPASS=\$(mktemp)
            cat > "\$ASKPASS" << 'EOF'
#!/bin/sh
# GitHub rejects account passwords. Always send a PAT via x-access-token.
case "\$1" in
  *[Uu]sername*) echo "x-access-token" ;;
  *) echo "\$GIT_PASSWORD" ;;
esac
EOF
            chmod 700 "\$ASKPASS"
            GIT_ASKPASS="\$ASKPASS" GIT_TERMINAL_PROMPT=0 git push origin "HEAD:refs/heads/${targetBranch}"
            rm -f "\$ASKPASS"

            git checkout "${sourceBranch}" || git checkout -B "${sourceBranch}" "origin/${sourceBranch}"
        """
    }
}
