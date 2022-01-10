# Data Engineer - Challenge
This is my solution for the Basic Challenge for Data Engineers in wallmart.

The Job is an ETL written in Java that executes on demand.

## Deployment 

1. The computer where the ETL is going to run has to have installed java 1.8 or greater

2. it is assumed that there is a MySQL server running remote, or in the same server where the ETL is going to be run.

3. Download the repository from the link https://github.com/pcorderojure/de-challenge/tree/master as a zip file

4. Extract fles in a computer folder and go to the root folder with a command console

5. Modify the file parameters.config located in the folder "config", and change all the parameters of the MySQL database. Specify the host and port of the database, and a user and password with enough privileges to create and delete databases. 

   To create MySQL user execute the following commands in MySQL server. If MySQL server is not located in the same machine where the ETL process is going to be run, be sure that the MySQL database engine admits remote user connections. To simplify, the fourth command assumes that both servers (ETL and MySQL) are located in the subnet 192.168.%. Change these parameters if the subnet is different or ignore the command is MySQL is installed on the same machine where ETL process is going to be run. In real productions systems, this permissions has to be improved for security:

    DROP USER IF EXISTS 'user-name'@'localhost';

    CREATE USER 'user-name'@'localhost' IDENTIFIED BY 'user-pass';

    GRANT ALL PRIVILEGES ON *.* TO 'user-name'@'localhost' IDENTIFIED BY 'user-pass' WITH GRANT OPTION;

    GRANT ALL PRIVILEGES ON *.* TO 'user-name'@'192.168.%' IDENTIFIED BY 'user-pass' WITH GRANT OPTION;

    FLUSH PRIVILEGES;


    NOTE: If MySQL server is located in the same machine where you are going to run the process do not consider the remote configuration.

6. When the configuration has been set, run the ETL project from the root folder of the project, executing the command:
  - java -jar wm_games.jar

7. Wait until the proccess finishes

8. Review all the output of the ETL

## Inputs and Outputs
- The input files (consoles.csv and result.csv) have to be located in "data" folder

- Log files are generated in "log" folder with each execution of the ETL process

- When records in the input files are discarded because of format errors or other causes, these records are informed in the log files, and the records are put it in the files  "consoles_not_loaded.csv" and" result_not_loaded.csv" inside "data" folder, for better analysis.

- Reports requested are generated in "report" folder

## Data Model
- The data model in 3NF is located in "DataModel" folder in three formats: 
  -   .png and .jpg as images
  -   .dbs format for the software DbSchema where the data model was designed. The software was selected because is an easy to use free tool, but powerful for modelate relational databases



