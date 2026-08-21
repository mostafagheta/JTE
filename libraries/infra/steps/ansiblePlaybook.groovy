void call(Map args) {
    def checkMode = args.check ? "--check" : ""
    echo "Running Ansible Playbook..."
    sh "ansible-playbook site.yml -i inventory ${checkMode}"
}
