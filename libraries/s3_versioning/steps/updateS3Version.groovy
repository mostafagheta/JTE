void call(Map args = [:]) {
    def bucket = resolveSetting(args, "bucket", "s3_bucket", "atos-versioning-bucket")
    def file = resolveSetting(args, "file", "s3_version_file", "version.json")

    echo "Updating S3 version file s3://${bucket}/${file} with current Git version.json..."
    sh """
        if [ -f "version.json" ]; then
            aws s3 cp version.json s3://${bucket}/${file}
            echo "Successfully synced version.json to S3."
        else
            echo "Warning: version.json not found in the repository!"
            exit 1
        fi
    """
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
