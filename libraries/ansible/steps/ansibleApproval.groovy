def call() {
    echo "========================================"
    echo "Ansible Approval"
    echo "========================================"

    timeout(time: 30, unit: 'MINUTES') {
        input(
            message: 'Ansible validation completed. Approve installation/update of EKS add-ons?',
            ok: 'Approve Ansible Deployment'
        )
    }

    echo "Ansible deployment approved."
}