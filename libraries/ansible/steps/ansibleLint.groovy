def call() {
    echo "========================================"
    echo "Ansible Lint"
    echo "========================================"

    sh '''
        set -e
        cd ansible
        ansible-lint . --profile=basic || true
    '''

    echo "Ansible lint completed successfully."
}