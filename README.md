# jamb

I created this project to illustrate the use of Jacoco code-coverage tool at a functional-testing level.
Though Jacoco is useful to analyze coverage for unit and integration testing level, it really shines
when it is used with RESTful APIs of Java web apps. 

The REST interfaces in this project return objects that follow the Siren spec.
The Siren spec is explained at https://github.com/kevinswiber/siren

Jamb (Just a Map as a Backend) uses a concurrentHashMap to store objects.
So, it doesn't survive restarts. 

## Pre-requisites
- JBoss server (preferably EAP 7.1.0) is installed 
- maven is installed

## Using Jacoco for Functional Testing of jamb's REST API

These are the steps involved in using the Jacoco Agent on a JBoss server
to identify code coverage among the different projects used to implement the Jamb web-app

### Build
- Use the maven command: `mvn clean install`
  
- Output should look something like:
```
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] jamb ............................................... SUCCESS [  0.371 s]
[INFO] JAMB Common Utils .................................. SUCCESS [  0.903 s]
[INFO] JAMB Service API ................................... SUCCESS [  0.182 s]
[INFO] JAMB Service ....................................... SUCCESS [  0.214 s]
[INFO] JAMB Service War ................................... SUCCESS [  0.487 s]
[INFO] JAMB Service REST Interface ........................ SUCCESS [  0.152 s]
[INFO] JAMB Service Functional Testing .................... SUCCESS [  1.152 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 3.557 s
[INFO] Finished at: 2019-05-08T14:34:12-05:00
[INFO] Final Memory: 39M/421M
[INFO] ------------------------------------------------------------------------
```

### Configuring Jacoco in JBoss
- I'm assuming that JBoss server is already installed

- ensure that JBoss server is currently stopped

- make backup of ${JBOSS_HOME}/bin/standalone.conf and use the file `jboss_eap_7_1_0_standalone_conf_with_jacoco_settings`
  from deployment folder as the new `standalone.conf` file
  
- make backup of ${JBOSS_HOME}/bin/standalone.sh and use the file `jboss_eap_7_1_0_standalone_sh_with_jacoco_settings`
  from deployment folder as the new `standalone.sh` file
  
- copy `deployment/jacocoagent.jar` file to /tmp directory

- copy `deployment/jacococli.jar` file to /tmp directory
  
- start JBoss

- the corresponding line printed for `ps -ef` command on the terminal should look like
```
jamb>ps -ef | grep jboss
  502 38077 38002   0  2:49PM ttys000    0:28.91 /usr/bin/java -D[Standalone] -server -verbose:gc -Xloggc:/Users/arajendran/EAP-7.1.0/standalone/log/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=3M -XX:-TraceClassUnloading -Xms1G -Xmx2G -XX:MetaspaceSize=196M -XX:MaxMetaspaceSize=1024M -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Xbootclasspath/p:/Users/arajendran/EAP-7.1.0/modules/system/layers/base/org/jboss/logmanager/commons/logging/main/commons-logging-jboss-logmanager-1.0.2.Final-redhat-1.jar -Xbootclasspath/p:/Users/arajendran/EAP-7.1.0/modules/system/layers/base/org/jboss/logmanager/main/jboss-logmanager-2.0.7.Final-redhat-1.jar -Xbootclasspath/p:/Users/arajendran/EAP-7.1.0/modules/system/layers/base/org/jboss/log4j/logmanager/main/log4j-jboss-logmanager-1.1.4.Final-redhat-1.jar -Xbootclasspath/p:/Users/arajendran/EAP-7.1.0/modules/system/layers/base/org/slf4j/impl/main/slf4j-jboss-logmanager-1.0.3.GA-redhat-2.jar -Djboss.modules.system.pkgs=org.jboss.logmanager -javaagent:/Users/arajendran/Documents/jacoco_jars/jacocoagent.jar=destfile=/tmp/total_jacoco.exec,excludes=/Users/arajendran/EAP-7.1.0/modules/**/*,jmx=true,classdumpdir=/tmp,dumponexit=true,append=false -Dorg.jboss.boot.log.file=/Users/arajendran/EAP-7.1.0/standalone/log/server.log -Dlogging.configuration=file:/Users/arajendran/EAP-7.1.0/standalone/configuration/logging.properties -jar /Users/arajendran/EAP-7.1.0/jboss-modules.jar -mp /Users/arajendran/EAP-7.1.0/modules org.jboss.as.standalone -Djboss.home.dir=/Users/arajendran/EAP-7.1.0 -Djboss.server.base.dir=/Users/arajendran/EAP-7.1.0/standalone -b=0.0.0.0
```
  
### Deployment and Execution
- Deploy `jamb-service-war.war` to JBoss

- run the Functional Test encapsulated in `Step100.java` under `jamb-functional-project` folder 
using the following command
`jamb-service-functional-testing>  mvn exec:java -Dexec.mainClass=com.ithellam.jamb.tests.Step000 -Dexec.args=100`

- Output should look something like:
```
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building JAMB Service Functional Testing 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ jamb-service-functional-testing ---
[com.ithellam.jamb.tests.Step000.main()] INFO com.ithellam.jamb.tests.Step000 - Started executing step number: 100
[com.ithellam.jamb.tests.Step000.main()] INFO com.ithellam.common.utils.IConfigProvider - Got value: http://localhost:8080/jamb/api for property name: com.ithellam.jamb.service.urls
[com.ithellam.jamb.tests.Step000.main()] INFO com.ithellam.jamb.tests.Step100 - Before creating C1
[com.ithellam.jamb.tests.Step000.main()] INFO com.ithellam.jamb.rest.client.ContainerClientResource - createContainer
[com.ithellam.jamb.tests.Step000.main()] INFO com.ithellam.jamb.tests.Step100 - After creating C1 : 8a9a65b5-96d0-4375-a9ed-477361e7e629
...
```

- Stop JBoss server

- jacoco agent which was running on JBoss will have created the execution data file at `/tmp/total_jacoco.exec` file

- run the following command which read the total_jacoco.exec file created above to generate user-readable HTML reports
```cd /tmp; java -jar jacococli.jar report total_jacoco.exec --classfiles ${HOME}/Documents/workspace/jamb/jamb-service/target --html ${HOME}/Documents/html_jacoco_output_jamb_service --sourcefiles ${HOME}/Documents/workspace/jamb/jamb-service/src/main/java ```

Note: '--sourcefiles' parameter is needed to see lines in the sourcefiles marked in green/red based on whether they were executed or not

- the HTML files generated under `${HOME}/Documents/html_jacoco_output_jamb_service` can be viewed in any browser to check the code coverage statistics








