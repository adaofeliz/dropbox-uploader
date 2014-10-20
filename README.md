Dropbox Uploader
=========

Dropbox Uploader is simple rest service to upload files into dropbox.

Technologies
----
Dropbox Uploader uses a number of open source projects to work properly:

* [Spring Framework] - The Spring Framework is a lightweight solution and a potential one-stop-shop for building your enterprise-ready applications.
* [Dropbox SDK] - The SDK wrap the raw HTTP calls to the Dropbox API.
* [Dropzone JS] - DropzoneJS is an open source library that provides drag'n'drop file uploads with image previews.

Requirements
----
You should create a new Application in [Dropbox App Console] and update the application.properties with your App key and App Secret. 


Installation
--------------
* Clone this project
```sh
git clone https://github.com/adaofeliz/dropbox-uploader.git dropbox-uploader
```

* Update App Properties file with your Application Details.

```sh
vi dropbox-uploader/dropbox-uploader-service/src/main/resources/application.properties
```
* Start Up the Application
```sh
cd dropbox-uploader
mvn clean install
cd dropbox-uploader-service/
mvn tomcat7:run
```

Try it
--------------
Now open your browser: http://localhost:8080/

![alt text](https://photos-6.dropbox.com/t/1/AAB2wCY5uaKCgNqkox812fTM3MZLaAOkUb93x0W_uJhutg/12/32917797/png/1024x768/3/1413831600/0/2/Screenshot%202014-10-20%2018.49.05.png/nneAP3cnfzqeSA3lBoPuud2XCF77lAO7AowbDv_EeQo)

You should allow your dropbox access using: http://localhost:8080/dropbox/allow

Your access token will be stored in your browser session cookies.


License
----

MIT

[Spring Framework]:http://docs.spring.io/spring-framework/docs/4.1.1.RELEASE/spring-framework-reference/html/
[Dropbox SDK]:https://www.dropbox.com/developers/core/sdks/java
[Dropzone JS]:http://www.dropzonejs.com/
[Dropbox App Console]:https://www.dropbox.com/developers/apps
