def call() {
    echo "========================================"
    echo "Ansible Lint"
    echo "========================================"

    sh '''
        set -e
        cd ansible
        ansible-lint . --parseable-severity --nofail
    '''

    echo "Ansible lint completed successfully."
}