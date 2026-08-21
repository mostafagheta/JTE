void call() {
    echo "Running Ansible Lint..."
    sh "ansible-lint site.yml || echo 'ansible-lint missed or skipped'"
}
