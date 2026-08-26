def call() {

    echo "========================================"
    echo "Ansible SSH Connectivity"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            ansible all \
                -i inventory.ini \
                -m ping
        '''
    }

    echo "SSH connectivity verified successfully."
}