package org.moya.core.memcached;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import zookeeper.groups.ListGroup;

import com.manybrain.persistent.MemCacheClient;

public class TestMoyaCluster {
	
	public static void main(String[] args) throws Exception{
		
		//Connect to zookeeper and get current moya nodes
		List<String> moyaServers = ListGroup.main(new String[]{"172.16.165.155","moya"});
		System.out.println(moyaServers.toString());
//		String[] servers = new String[moyaServers.size()];
//		String[] ports = new String[moyaServers.size()];
//		
//		//Add all memcahed servers in moya
//		int count = 0;
//	    for ( Iterator<String> moyaIter = moyaServers.iterator(); moyaIter.hasNext(); ) {
//	    	String line = moyaIter.next();
//	        servers[count] = line.split("\\d*.\\d*.\\d*.\\d*")[0].trim();
//	        ports[count] = line.split("(\\d*$)")[0].trim();
//	        count++;
//	      }
	    
	    
	    //Setup all the moya servers
	    MemcachedClient mcc=new MemcachedClient(AddrUtil.getAddresses(moyaServers));

	    	// Store a value (async) for one hour
	    	//c.set("someKey", 3600, someObject);
	    	// Retrieve a value (synchronously).
	    	//Object myObject=c.get("someKey");
	    	
	    
	    
	    //We want to use both moya nodes equally
		int[] weights = new int[] { 1, 1 }; 
		//MemCacheClient mcc = new MemCacheClient(servers, weights);
		
		//Lets load up the cache
		Random rand = new Random();

		//POPULATE
		for(int x = 0; x < 100; x++){
			int value = rand.nextInt();
			System.out.println("Adding Key: " + x + " Value: " + value);
			
			mcc.set(String.valueOf(x), 3600, value);
			Thread.sleep(50);
		}
		

		//GET 
		for(int r = 0; r < 100; r++){
			System.out.println("Got Value: "+ (mcc.get(String.valueOf(r)))
			+" From Key: " + r);
			
			Thread.sleep(50);
		}
		
		//Print out the server stats
		Map<SocketAddress, Map<String, String>> stats = mcc.getStats();
		Set<Entry<SocketAddress, Map<String, String>>> re = stats.entrySet();
		Iterator<Entry<SocketAddress, Map<String, String>>> setIter = re.iterator();
		
		while(setIter.hasNext()){
			Entry<SocketAddress, Map<String, String>> entr = setIter.next();
			String SocketAdder = entr.getKey().toString();
			
			Iterator<Entry<String, String>> inner = ((Map<String, String>)entr.getValue()).entrySet().iterator();
			while(inner.hasNext()){
				Entry<String, String> entrI = inner.next();
				System.out.println(SocketAdder+":"+entrI.getKey()+" "+entrI.getValue());
			}
		}
		
	}

}
