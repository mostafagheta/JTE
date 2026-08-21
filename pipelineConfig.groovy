/* 
  JTE Global Configuration for App and Infra 
  This maps libraries to environments or specific templates. 
*/
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
