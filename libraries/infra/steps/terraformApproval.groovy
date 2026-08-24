def call() {

    def action = params.ACTION?.toLowerCase()

    if (action == 'destroy') {

        timeout(time: 30, unit: 'MINUTES') {
            input(
                message: 'WARNING: This Terraform plan will DESTROY infrastructure. Approve to continue?',
                ok: 'Approve Destroy'
            )
        }

    } else {

        timeout(time: 30, unit: 'MINUTES') {
            input(
                message: 'Terraform plan passed all checks. Approve the infrastructure changes?',
                ok: 'Approve Apply'
            )
        }
    }
}