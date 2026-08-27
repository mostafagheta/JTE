def call() {

    echo "========================================"
    echo "Ansible Verification"
    echo "========================================"

    sshagent(credentials: ['mykey']) {

        sh '''
            set -e

            mkdir -p ~/.ssh
            chmod 700 ~/.ssh

            BASTION_HOST=$(ansible-inventory -i inventory.ini --host bastion | grep -m1 '"ansible_host"' | cut -d'"' -f4)
            if [ -z "$BASTION_HOST" ]; then
                BASTION_HOST=$(awk '/^\\[bastion\\]/{getline; print $1; exit}' inventory.ini)
            fi

            ssh-keyscan -H "$BASTION_HOST" >> ~/.ssh/known_hosts 2>/dev/null

            ansible bastion -i inventory.ini -m ping
            ansible bastion -i inventory.ini -m shell -a "hostname"
        '''
    }

    echo "Ansible verification completed successfully."
}