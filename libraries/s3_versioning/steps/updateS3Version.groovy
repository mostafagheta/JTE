void call() {
    def bucket = pipelineConfig.s3_bucket
    def file = pipelineConfig.s3_version_file
    
    echo "Updating S3 version file with current Git version.json..."
    sh """
        if [ -f "version.json" ]; then
            aws s3 cp version.json s3://${bucket}/${file}
            echo "Successfully synced version.json to S3."
        else
            echo "Warning: version.json not found in the repository!"
        fi
    """
}
