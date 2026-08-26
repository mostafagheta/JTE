def call() {
    echo "========================================"
    echo "Ansible Lint"
    echo "========================================"

    sh '''
        set -e
        cd ansible
        ansible-lint .
    '''

    echo "Ansible lint completed successfully."
}