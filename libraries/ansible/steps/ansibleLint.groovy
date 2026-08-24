def call() {
    echo "========================================"
    echo "Ansible Lint"
    echo "========================================"

    sh '''
        set -e

        ansible-lint .
    '''

    echo "Ansible lint completed successfully."
}