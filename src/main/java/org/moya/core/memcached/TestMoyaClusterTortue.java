package org.moya.core.memcached;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import zookeeper.groups.ListGroup;

import com.manybrain.persistent.MemCacheClient;

public class TestMoyaClusterTortue {
	
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
		for(int x = 0; x < 10000; x++){
			int value = rand.nextInt();			
			mcc.set(String.valueOf(x*20000), 3600, value);
			Thread.sleep(5);
		}
		
		//Don't load faster then memcahce can... 
//		LinkedList<Future<Boolean>> operationBuffer = new LinkedList<Future<Boolean>>();
//
//		for(int i = 0 ; i < 10000; i++)
//		{
//		    operationBuffer.add(mcc.set(String.valueOf(i), 120, rand.nextInt())); 
//
//		    if(operationBuffer.size() >= 100)
//		    {
//		        operationBuffer.removeFirst().get(25, TimeUnit.SECONDS);
//		    }
//		}

		//GET 
		for(int r = 0; r < 10000; r++){
			mcc.get(String.valueOf(20000*rand.nextInt(10000)+1));
			Thread.sleep(1);
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
