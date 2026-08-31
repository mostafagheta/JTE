def call() {

    echo "========================================"
    echo "Ansible Syntax Check"
    echo "========================================"

    sh '''
        set -e

        ansible-playbook \
            -i inventory.ini \
             ansible/playbooks/install_addons.yml \
            --syntax-check
    '''

    echo "Ansible syntax check completed successfully."
}