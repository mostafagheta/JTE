def call() {

    echo "========================================"
    echo "Ansible Check Mode"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            ansible-playbook \
                -i inventory.ini \
                ansible/playbooks/install_addons.yml \
                --check \
                --diff
        '''
    }

    echo "Ansible check mode completed."
}