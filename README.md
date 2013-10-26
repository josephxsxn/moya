Memcached on YARN - Version 0.10-Core Alpha 
====

## Futures ##
* Configure alloted ram for **Demon currently requires a 512mb container to function** as 448mb goes to the JMemcachD Deamon
* Getting containers that die to automatically restart
* Get the Application Master to restart if it dies
* Management of the clients. Currently I have to kill clients through the YARN Cli. 

      
      `yarn application -kill [app#] `
      

* Adding in unit tests and sample/test applications 
* Client Memcached system notification if a server daemon dies. 
* Have MOYA clean things up if the AM dies or is exited. 
* Configure Evition Policy, currently set as **FIFO**
* Configure Number of Keys to handle, currently **set at 1million per Daemon**
* Migrate paramaters to configeration file 


## Usage ##
```
yarn jar [MOYA-CLIENT jar] org.moya.core.yarn.Client 

usage: Client
 -appname <arg>            Optional: Application Name. Default value - MoYa
 -container_memory <arg>   Recommended: Amount of memory in MB to be requested to run
                           the shell command - Defaults to 10, Recommended is 512. 
 -debug                    Optional: Dump out debug information
 -help                     Optional: Print usage
 -jar <arg>                Required: Jar file containing the application master - MOYA-CLIENT jar
 -lib <arg>                Required: Runnable Jar with MOYA inside - MOYA-SERVER jar
 -log_properties <arg>     Optional: log4j.properties file
 -master_memory <arg>      Recommended: Amount of memory in MB to be requested to run
                           the application master - Defaults to 10, Recommended is 128
 -moya_priority <arg>      Optional: Priority for the MOYA containers - Defaults to 0
 -num_containers <arg>     Recommended: No. of containers on which the shell command
                           needs to be executed, Defaults to 1
 -priority <arg>           Optional: Application Priority - Default 0
 -queue <arg>              Optional: RM Queue in which this application is to be
                           submitted - Defaults to 'default'
 -ZK <arg>                 Required: Comma seperated list of ZK hosts ie -
                           host1:port,host2:port

```

## Example ##
```
sudo -u hdfs 
yarn jar MOYA-CLIENT-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.moya.core.yarn.Client 
-jar MOYA-CLIENT-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
-lib MOYA-SERVER-0.0.1-SNAPSHOT-jar-with-dependencies.jar  
-num_containers 10 
-container_memory 512 
-ZK 192.168.17.52:2181

```
## MOYABeatDown ##

The Client jar also includes the MOYABeatDown class which will load up some KV Pairs and Then get them. 
`org.moya.core.memcached.MOYABeatDown [ZKServerList] [#ofKeysToMake] [OffsetWhenGettingKeys]`

* Example which will create 1% misses
```
java -cp MOYA-CLIENT-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.moya.core.memcached.MOYABeatDown 192.168.17.52:2181 100000 1000
```
