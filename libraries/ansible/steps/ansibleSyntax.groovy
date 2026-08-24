def call() {
    echo "========================================"
    echo "Ansible Syntax Check"
    echo "========================================"

    sh '''
        set -e

        ansible-playbook \
            playbooks/eks-addons.yml \
            --syntax-check
    '''

    echo "Ansible syntax validation completed."
}