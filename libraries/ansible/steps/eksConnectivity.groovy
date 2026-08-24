def call() {
    echo "========================================"
    echo "EKS Connectivity"
    echo "========================================"

    sh '''
        set -e

        echo "Checking AWS identity..."

        aws sts get-caller-identity

        echo "Updating kubeconfig..."

        aws eks update-kubeconfig \
            --region "$AWS_REGION" \
            --name "$EKS_CLUSTER"

        echo "Checking Kubernetes API..."

        kubectl cluster-info

        echo "Checking cluster access..."

        kubectl get nodes
    '''

    echo "EKS connectivity verified."
}