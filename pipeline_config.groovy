/*
  JTE Global Configuration for App and Infra
  @merge lets application pipeline_config.groovy add library parameters and keywords.
*/
@merge jte {
    allow_scm_jenkinsfile = false
}

@merge libraries {
    s3_versioning
    java_build
    security_scan
    registry
    gitops
    deploy
    infra
    infra_validation
    common
}

@merge keywords {
}

application_environments {
    dev
    test
    prod
}
