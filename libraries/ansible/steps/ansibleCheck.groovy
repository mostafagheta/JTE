def call() {

    echo "========================================"
    echo "Ansible Check Mode"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            ansible-playbook \
                -i inventory.ini \
                playbooks/eks-addons.yml \
                --check \
                --diff
        '''
    }

    echo "Ansible check mode completed."
}