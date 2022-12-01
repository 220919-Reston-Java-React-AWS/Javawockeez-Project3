# Local Start Up

### Creating and Setting Up the database 

**Note:** The assumption is that you already know how to set up/create a database locally on your device.

1. First create a new database on your machine (PostgreSQL preferrably).

2. Enter the main/resources folder and configure the application.yml file to connect to the database 

    Located in the main/resources folder, there is the application.yml file required to configure connecting to a database. Based what you plan to do, there two options:

    - Application.yml file is configure for a PostgreSQL database. All you need to do is edit the datasource url, username, and password to point to your database.

    - If you plan on using a different type of SQL database, then edit the file to reflect the database platform and dialect driver and change the datasource properties to point to your database.

    **Note**: When initailizing your database for the first time, change the hibernate ddl-auto property to 'create' and change back to 'update.'

### Setting Up Maven

Maven is dependency manager and build tool. 

1. Install Maven to computer. Go to the Apache Maven website for downloading Maven and instructions how to install.

2. Using the provided pom.xml file, if your IDE allows to, use the built-in build tool to build the Maven dependencies for the project to run.

### Running the Program

Open up SocialMediaApplication.java file and run the file.


# Starting Up on AWS

### Necessary files for AWS

We will be using AWS for hosting and deploying our backend server. There are 2 required files for this.

1. The Dockerfile contains the process for the AWS pipeling to dockerize the backend.

2. The buildspec.yml file contains the process for CodeBuild to build the project on the cloud.

### Setting Up the AWS Pipeline

You should first create a AWS account if you don't have one already. Then follow the following steps:

        Go to elastic beanstalk

        Choose docker for platform

        Codepipeline in searchbar

        Create a pipeline

        Github version 2 for provider

        Connect to github, name it whatever

        Then install new app

        Click rev org then install then connect then choose your repository name then branch main then next

        Build provider is aws code build

        Then create project and name

        Restrict conccurrent builds to one

        Change OS to ubunut

        Change image to 5

        Put group name and stream name then configure then next

        Deploy elastiv beanstalk

        Create pipeline

### Test the Pipeline

After you're done with the section above, your github repo with the Spring server should be connected. The last step is to push a see if the pipeline and build and deploy the project.

If any errors occur, then check the watch logs to figure out what you need to do to fix and successfully execute the pipleine.