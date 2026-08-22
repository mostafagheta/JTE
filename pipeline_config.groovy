/*
  JTE Global Configuration for App and Infra
  JTE 2.x looks for pipeline_config.groovy (not pipelineConfig.groovy).
*/
jte {
    allow_scm_jenkinsfile = false
}

libraries {
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

application_environments {
    dev
    test
    prod
}
