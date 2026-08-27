def call() {

    echo "========================================"
    echo "Ansible Check Mode"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            export ANSIBLE_ROLES_PATH=/home/jenkins/workspace/ansible/ansible/roles
            ansible bastion -i inventory.ini -m raw -a "sudo yum install -y python3-pip" --become 
            set +e
            ansible-playbook \
                -i inventory.ini \
                ansible/playbooks/install_addons.yml \
                --check \
                --diff
            CHECK_EXIT=$?
            set -e

            if [ $CHECK_EXIT -ne 0 ]; then
                echo "WARNING: Ansible check mode reported issues (exit code $CHECK_EXIT). Review diff output above."
            else
                echo "Ansible check mode passed with no issues."
            fi
        '''
    }

    echo "Ansible check mode completed."
}