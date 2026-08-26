def call() {

    echo "========================================"
    echo "Ansible Verification"
    echo "========================================"


        withCredentials([file(credentialsId: 'mykey', variable: 'SSH_KEY')]) {
        sh '''
            set -e

            # Set key permissions
            chmod 600 $SSH_KEY

        ansible bastion -i inventory.ini -m ping --private-key $SSH_KEY
        ansible bastion -i inventory.ini -m shell -a "hostname" --private-key $SSH_KEY
        '''
    }

    echo "Ansible verification completed successfully."
}