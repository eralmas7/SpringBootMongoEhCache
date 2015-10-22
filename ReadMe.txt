- Assumptions:
  > JDK 8 is installed on the machine.
  > Maven is installed on the machine.
  > If development needs to be done then any IDE like eclipse, intellij etc.
  > I have just included functionality to get chat or blog data from the client.
  > User would prefer going to /src/main/docs to refer to documents like technical design document, wink file, class diagram


- Instructions to install and configure any prerequisites for the development environment
  > Install mongo db on local machine
    Goto https://www.mongodb.org/downloads?_ga=1.205504016.1432625826.1445100649#production
    Select appropropriate OS
    Select version from dropdown and your download will start
    install the mongo db
    Once installed, you could run the following command:
    mongod.exe --dbpath <path of mongo repository that has process's owner write permission>
  > To build the code and executable jar, just run from root directory of the source
    mvn clean install
  > You would need following command to run the application
    java -jar target/boot-rest-api-0.1.jar