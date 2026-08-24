def call() {
    echo "========================================"
    echo "Ansible Dependencies"
    echo "========================================"

    sh '''
        set -e

        if [ -f requirements.yml ]; then
            echo "Installing Ansible collections..."

            ansible-galaxy collection install \
                -r requirements.yml \
                --force
        else
            echo "No requirements.yml found."
        fi
    '''

    echo "Ansible dependencies completed."
}