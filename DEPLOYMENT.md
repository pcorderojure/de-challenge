# Data Engineer - Challenge
This is my solution for the Basic Challenge for Data Engineers in wallmart.

The Job is an ETL code writed in Java that runs as a batch process.

## Deployment 
1. Download the repository from the link https://github.com/pcorderojure/de-challenge/tree/master as a zip file

2. The computer where the ETL is going to run has to have installed java 1.8 or greater

3. Extract fles in a computer folder and go to the root folder with a command console

4. Modify the file parameters.config located in the folder "config" and change all the parameters of the MySQL database. Specify the host and port of the database, and a user and password with enough privileges to create and delete databases. Be sure that the MySQL database engine selected admits remote user connections, and the user has permissions on the host. To simplify, you can give access to the user, executing the following command, where the addresses are in the subnet 192.168.%. In real productions systems, this connection has to be improved for security:

  - GRANT ALL PRIVILEGES ON *.* TO 'user-name'@'192.168.%' IDENTIFIED BY 'user-password' WITH GRANT OPTION;

5. When the configuration has been set, run the ETL project from the root folder of the project, executing the command:
  - java -jar wm_games.gar

6. Wait until the proccess finishes

7. Review all the output of the ETL

## Outputs
- The imput files (consoles.csv and result.csv) have to be located in "data" folder

- Log files are generated with each execution of the ETL in "log" folder

- When records in the input files are discarded because of format errors, these records are informed in the log files, and the records are put in files  "consoles_not_loaded.csv" and" result_not_loaded.csv " inside "data" folder for better analysis.

- Reports are generated in "report" folder

- The data model in 3NF is located in "DataModel" folder in three formats: 
  -   .png and .jpg as images
  -   .dbs format for the software DbSchema where the data model was designed, because is an easy to use free tool, but powerful for modelate relational databases



