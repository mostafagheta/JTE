def call() {
    echo "========================================"
    echo "Terraform Plan"
    echo "========================================"

    if (params.ACTION == 'destroy') {

        echo "Terraform action: DESTROY"

        sh '''
            set -e

            terraform plan \
                -destroy \
                -input=false \
                -out=tfplan

            terraform show \
                -no-color \
                tfplan > terraform-plan.txt
        '''

    } else {

        echo "Terraform action: APPLY"

        sh '''
            set -e

            terraform plan \
                -input=false \
                -out=tfplan

            terraform show \
                -no-color \
                tfplan > terraform-plan.txt
        '''
    }

    archiveArtifacts artifacts: 'tfplan,terraform-plan.txt',
                     fingerprint: true
}