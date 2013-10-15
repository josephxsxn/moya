MOYA - JMemcacheD Server Daemon On YARN
=======================================


*KNOWN ISSUES*

MAJOR -
Server Daemons are setup as FIFO Eviction ONLY
Server Daemons don’t start to eject keys until after
	a) 1,000,000 Keys have been placed on the Daemon
	b) Expect that containers are at least 512MB in size. The Server Daemons are set to use 448MB before evictions starts.  


MINOR - 
Containers that die do not restart
If the AM dies it does not restart
No way to kill long running job from client. YARN Cli only.
yarn application -kill [app#]
No tests only beatdown client to load the system
Client Memcached system does not update if a server daemon dies.
Initial MOYA node in ZK needs to remove itself if the AM dies or is exited.
Only 1 runnable Daemon per node as they cannot share the same port


*USAGE EXAMPLE*
-jar  This is the Application Master Jar
-lib  This is the Runnable Jar which will run the Server Daemon 
-num_containers This is the number of Server Daemons you want to spawn
-container_memory The number of MB’s of RAM each Server Daemon gets
-ZK  The comma separated list of ZooKeeper servers and their port.  

yarn jar moya-core.jar org.moya.core.yarn.MemClient -jar moya-core.jar -lib moya-core-RUNNABLE.jar -num_containers 10 -container_memory 512 -ZK 192.168.17.52:2181


