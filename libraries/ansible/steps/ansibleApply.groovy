def call() {

    echo "========================================"
    echo "Ansible Apply"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            ansible-playbook \
                -i inventory.ini \
                playbooks/eks-addons.yml
        '''
    }

    echo "Ansible deployment completed successfully."
}