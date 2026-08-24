def call() {
    echo "========================================"
    echo "EKS Add-ons Verification"
    echo "========================================"

    sh '''
        set -e

        echo "Checking Kubernetes nodes..."

        kubectl get nodes

        echo "Checking pods..."

        kubectl get pods -A

        echo "Checking Helm releases..."

        helm list -A
    '''

    echo "EKS add-on verification completed."
}