def call() {

    echo "========================================"
    echo "Validate Ansible Inventory"
    echo "========================================"

    if (!fileExists('inventory.ini')) {
        error('inventory.ini does not exist')
    }

    sh '''
        set -e

        echo "Inventory:"
        cat inventory.ini

        echo "Testing inventory parsing..."

        ansible-inventory \
            -i inventory.ini \
            --graph

        ansible-inventory \
            -i inventory.ini \
            --list > /dev/null

        echo "Inventory validation completed successfully."
    '''
}