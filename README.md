moya
====

Memcached on YARN


Futures -
1.	Getting containers that die to automatically restart
2.	Understand how I can get the Application Master to restart if it dies
3.	Management of the clients. Currently I have to kill clients through the YARN Cli. 
      yarn application -kill [app#] 
4.	Adding in unit tests and sample/test applications 
5.	Client Memcached system notification if a server daemon dies. 
6.	Have MOYA clean things up if the AM dies or is exited. 
7. Configure alloted ram for Demon currently requires a 512mb container to function as 448mb goes to the JMemcachD Deamon
8. Configure Evition Policy, currently set as FIFO
9. Configure Number of Keys to handle, currently set at 1million per Daemon



Usage-
yarn jar [client jar] [clientclass] -jar [jar with AM (client jar)] -lib [runnable Server Daemon] 
-num_containers [# of Daemons to start] -container_memory [MB's of Ram for the YARN Container] 
-ZK [host:port comma seperated list of ZooKeeper Servers]

Example - 
sudo -u hdfs 
yarn jar MOYA-CLIENT-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.moya.core.yarn.Client 
-jar MOYA-CLIENT-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
-lib MOYA-SERVER-0.0.1-SNAPSHOT-jar-with-dependencies.jar  
-num_containers 10 
-container_memory 512 
-ZK 192.168.17.52:2181


The Client jar also includes the MOYABeatDown class which will load up some KV Pairs and Then get them. 
org.moya.core.memcached.MOYABeatDown [ZKServerList] [#ofKeysToMake] [OffsetWhenGettingKeys]

Example which will create 1% misses
java -cp MOYA-CLIENT-0.0.1-SNAPSHOT-jar-with-dependencies.jar org.moya.core.memcached.MOYABeatDown 192.168.17.52:2181 100000 1000

