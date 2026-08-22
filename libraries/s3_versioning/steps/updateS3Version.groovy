void call(Map args = [:]) {
    def bucket = args.bucket ?: config.s3_bucket ?: config.bucket
    def file = args.file ?: config.s3_version_file ?: config.file

    if (!bucket || !file) {
        error("s3_versioning is missing s3_bucket/s3_version_file. Set them under libraries { s3_versioning { ... } } in the app pipeline_config.groovy, and annotate libraries with @merge in the governance config.")
    }

    echo "Updating S3 version file with current Git version.json..."
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
