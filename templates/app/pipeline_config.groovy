/*
  JTE Global Configuration
  Library parameters live here (first config in the chain) so they are not
  stripped when the application pipeline_config.groovy is merged.
*/
@merge jte {
    pipeline_template = "Jenkinsfile"
}

@merge libraries {
    s3_versioning {
        s3_bucket = "atos-versioning-bucket"
        s3_version_file = "version.json"
    }
    java_build
    security_scan {
        sonar_project_key = "spring-petclinic-main"
        sonar_token_credential = "spring-petclinic-main"
        sonar_server = "SonarQube"
        sonar_agent = "master"
        ecr_registry = "130299714330.dkr.ecr.eu-central-1.amazonaws.com"
        ecr_repo = "petclinic"
    }
    registry {
        ecr_registry = "130299714330.dkr.ecr.eu-central-1.amazonaws.com"
        ecr_repo = "petclinic"
        aws_region = "eu-central-1"
    }
    git 
}

@merge keywords {
    app_name = "spring-petclinic"
    s3_bucket = "atos-versioning-bucket"
    s3_version_file = "version.json"
    ecr_registry = "130299714330.dkr.ecr.eu-central-1.amazonaws.com"
    ecr_repo = "petclinic"
}

application_environments {
    dev
    test
    prod
}
