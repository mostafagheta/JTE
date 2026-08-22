/*
  JTE Global Configuration
  Library parameters live here (first config in the chain) so they are not
  stripped when the application pipeline_config.groovy is merged.
*/
@merge jte {
    allow_scm_jenkinsfile = false
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
        ecr_registry = "130299714330.dkr.ecr.eu-central-1.amazonaws.com"
        ecr_repo = "petclinic"
    }
    registry {
        ecr_registry = "130299714330.dkr.ecr.eu-central-1.amazonaws.com"
        ecr_repo = "petclinic"
        aws_region = "eu-central-1"
    }
    gitops {
        gitops_repo = "https://github.com/mostafagheta/gitops-repo.git"
        git_credential = "40064b7c-67f3-4b2c-8d3d-57d801eb56c3"
    }
    deploy
    infra
    infra_validation
    common
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
