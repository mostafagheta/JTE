def call(Map args = [:]) {
    def bucket = args.bucket ?: config.s3_bucket ?: config.bucket
    def file = args.file ?: config.s3_version_file ?: config.file

    if (!bucket || !file) {
        error("s3_versioning is missing s3_bucket/s3_version_file. Set them under libraries { s3_versioning { ... } } in the app pipeline_config.groovy, and annotate libraries with @merge in the governance config.")
    }

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
