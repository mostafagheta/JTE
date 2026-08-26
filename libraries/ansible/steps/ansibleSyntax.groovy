def call() {

    echo "========================================"
    echo "Ansible Verification"
    echo "========================================"

    withCredentials([sshUserPrivateKey(
        credentialsId: 'mykey', 
        keyFileVariable: 'SSH_KEY',
        usernameVariable: 'SSH_USER'
    )]) {
        sh '''
            set -e
            export ANSIBLE_HOST_KEY_CHECKING=False
            # Set key permissions
            chmod 600 $SSH_KEY

    echo "--- raw echo test ---"
    ansible bastion -i inventory.ini -m raw -a "echo ANSIBLE_MARKER_START; echo hello; echo ANSIBLE_MARKER_END" --private-key $SSH_KEY

    echo "--- python version ---"
    ansible bastion -i inventory.ini -m raw -a "python3 --version" --private-key $SSH_KEY

    echo "--- verbose ping ---"
    ansible bastion -i inventory.ini -m ping --private-key $SSH_KEY -vvvv
        '''
    }

    echo "Ansible verification completed successfully."
}