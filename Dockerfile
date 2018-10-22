FROM payara/server-full:5-SNAPSHOT

COPY target/hello-javaee8.war $DEPLOY_DIR

