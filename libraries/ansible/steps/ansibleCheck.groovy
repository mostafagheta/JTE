def call() {

    echo "========================================"
    echo "Ansible Check Mode"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            export ANSIBLE_ROLES_PATH=/home/jenkins/workspace/ansible/ansible/roles
            ansible-playbook \
                -i inventory.ini \
                ansible/playbooks/install_addons.yml \
                --check \
                --diff
        '''
    }

    echo "Ansible check mode completed."
}