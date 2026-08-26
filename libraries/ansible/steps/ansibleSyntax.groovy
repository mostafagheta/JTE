def call() {

    echo "========================================"
    echo "Ansible Verification"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            ansible bastion \
                -i inventory.ini \
                -m ping

            ansible bastion \
                -i inventory.ini \
                -m shell \
                -a "hostname"
        '''
    }

    echo "Ansible verification completed successfully."
}