def call() {
    echo "========================================"
    echo "Ansible Apply"
    echo "========================================"

    sh '''
        set -e

        ansible-playbook \
            playbooks/eks-addons.yml
    '''

    echo "Ansible deployment completed successfully."
}