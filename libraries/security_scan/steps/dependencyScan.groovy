void call() {
    echo "Running Dependency Scanning..."
    // DC 13 treats NVD_API_KEY="" as invalid ("length of 0") and then has no CVE DB.
    // Use the public NVD JSON 2.0 feeds so the scan does not need an API key.
    sh '''
        if [ -z "${NVD_API_KEY:-}" ]; then
          echo "NVD_API_KEY is empty; unsetting it so Dependency-Check does not send a blank key."
          unset NVD_API_KEY
        fi
        ./mvnw -B org.owasp:dependency-check-maven:13.0.0:check \
          -DnvdDatafeedUrl=https://nvd.nist.gov/feeds/json/cve/2.0/nvdcve-2.0-{0}.json.gz
    '''
}
