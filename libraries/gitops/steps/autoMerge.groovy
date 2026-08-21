void call(Map config) {
    def targetBranch = config.targetBranch
    def sourceBranch = env.BRANCH_NAME
    
    echo "Automated Merge: Merging ${sourceBranch} into ${targetBranch}"
    
    // Configure Git for automated commits
    sh """
        git config user.email "jenkins-auto-merger@atos.net"
        git config user.name "Jenkins Auto Merger"
        
        # Ensure we have the latest branches
        git fetch origin
        
        # Checkout the target branch
        git checkout ${targetBranch} || git checkout -b ${targetBranch} origin/${targetBranch}
        
        # Merge the source branch into the target branch
        git merge origin/${sourceBranch} -m "chore(auto-promote): Merge ${sourceBranch} into ${targetBranch} triggered by Jenkins build ${env.BUILD_NUMBER}"
        
        # Push to remote
        git push origin ${targetBranch}
        
        # Switch back to original branch so pipeline isn't disrupted
        git checkout ${sourceBranch}
    """
}
