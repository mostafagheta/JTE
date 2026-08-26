def call() {
    echo "========================================"
    echo "Ansible Dependencies"
    echo "========================================"

    sh '''
    set -e

    if [ -f ansible/requirements.yml ]; then
        echo "Installing Ansible collections..."
        
        ansible-galaxy collection install \
            -r ansible/requirements.yml \
            --force
    else
        echo "No requirements.yml found in ansible/ directory."
    fi
    '''
    

    echo "Ansible dependencies completed."
}