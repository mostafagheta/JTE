void call(Map config) {
    def bucket = config.bucket ?: pipelineConfig.s3_bucket
    def file = config.file ?: pipelineConfig.s3_version_file
    
    echo "Checking Git commit against S3 version file at s3://${bucket}/${file}..."
    
    def hasChanges = false
    try {
        sh """
            # Download the S3 version file
            aws s3 cp s3://${bucket}/${file} current_s3_version.json || echo '{"version": ""}' > current_s3_version.json
            
            # Read versions utilizing jq
            S3_VERSION=\$(jq -r '.version' current_s3_version.json)
            GIT_VERSION=\$(jq -r '.version' version.json)
            
            echo "Version on S3: \$S3_VERSION"
            echo "Version on Git: \$GIT_VERSION"
            
            if [ "\$S3_VERSION" == "\$GIT_VERSION" ] && [ -n "\$GIT_VERSION" ]; then
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
