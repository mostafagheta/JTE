def call(Map args = [:]) {
    def bucket = resolveSetting(args, "bucket", "s3_bucket", "atos-versioning-bucket")
    def file = resolveSetting(args, "file", "s3_version_file", "version.json")

    echo "Checking Git commit against S3 version file at s3://${bucket}/${file}..."

    def hasChanges = false
    try {
        sh """
            set +e
            aws s3 cp s3://${bucket}/${file} current_s3_version.json
            if [ \$? -ne 0 ]; then
                echo '{"version": ""}' > current_s3_version.json
            fi

            S3_VERSION=\$(jq -r '.version' current_s3_version.json)
            GIT_VERSION=\$(jq -r '.version' version.json)

            echo "Version on S3: \$S3_VERSION"
            echo "Version on Git: \$GIT_VERSION"

            if [ "\$S3_VERSION" = "\$GIT_VERSION" ] && [ -n "\$GIT_VERSION" ]; then
                echo "Version matches. No changes detected."
                echo "false" > HAS_CHANGES
            else
                echo "Version differs. Pipeline will proceed."
                echo "true" > HAS_CHANGES
            fi
        """
        def result = readFile('HAS_CHANGES').trim()
        hasChanges = result == "true"
    } catch (Exception e) {
        echo "Error checking S3 version, proceeding with pipeline... (${e.message})"
        hasChanges = true
    }

    return hasChanges
}

def resolveSetting(Map args, String argKey, String configKey, String fallback) {
    def value = asText(args[argKey])
    if (value) { return value }
    value = asText(config[configKey])
    if (value) { return value }
    value = asText(config[argKey])
    if (value) { return value }
    try {
        value = asText(jte.keywords[configKey])
        if (value) { return value }
    } catch (Exception ignored) {}
    return fallback
}

def asText(def value) {
    if (value == null) { return null }
    if (value instanceof Map) { return null }
    String text = "${value}".trim()
    if (text.length() == 0 || text == "null" || text == "[:]") { return null }
    return text
}
