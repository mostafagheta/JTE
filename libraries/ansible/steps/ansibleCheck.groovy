def call() {
    echo "========================================"
    echo "Ansible Check Mode"
    echo "========================================"

    sh '''
        set -e

        ansible-playbook \
            playbooks/eks-addons.yml \
            --check \
            --diff
    '''

    echo "Ansible check mode completed."
}