/*
  JTE Global Configuration
*/
jte {
    pipeline_template = "templates/terraform_pipeline"
}

libraries {
    s3_versioning {
        s3_bucket = "atos-versioning-bucket"
        s3_version_file = "infra_version.json"
    }
    infra
    common
}