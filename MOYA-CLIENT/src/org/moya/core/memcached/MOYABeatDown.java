package org.moya.core.memcached;

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

public class MOYABeatDown {
	
	public static void main(String[] args) throws Exception{
		
		if(args.length < 3)
		{ System.out.println("Usage - ZKHosts String, # of KV to make, Offset to create Misses in Get");
		return;
		}
		
		String ZKHost = args[0];
		Integer beatDownValue = Integer.parseInt(args[1]);
		Integer missRate = Integer.parseInt(args[2]);
		
		//Connect to zookeeper and get current moya nodes
		List<String> moyaServers = ListGroup.main(new String[]{ZKHost,"moya"});
		System.out.println(moyaServers.toString());
	    
	    
	    //Setup all the moya servers
	    MemcachedClient mcc=new MemcachedClient(AddrUtil.getAddresses(moyaServers));
   
	    
	    //We want to use both moya nodes equally
		
		//Lets load up the cache
		Random rand = new Random();

		//POPULATE
		for(int x = 0; x < beatDownValue; x++){
			int value = rand.nextInt();			
			mcc.set(String.valueOf(x), 3600, value); //store the value for 1 hour = 3600 seconds
			Thread.sleep(2);
		}

		//GET 
//		for(int r = 0; r < beatDownValue; r++){
//			mcc.get(String.valueOf(rand.nextInt(beatDownValue)+1+Miss));
//			Thread.sleep(1);
//		}
		
		for(int r = 0; r < beatDownValue; r++){
			int key = r+missRate;
			mcc.get(String.valueOf(key));
			Thread.sleep(1);
		}
		
		//Print out the server stats
		Map<SocketAddress, Map<String, String>> stats = mcc.getStats();
		Set<Entry<SocketAddress, Map<String, String>>> re = stats.entrySet();
		Iterator<Entry<SocketAddress, Map<String, String>>> setIter = re.iterator();
		
		while(setIter.hasNext()){
			
			Entry<SocketAddress, Map<String, String>> entr = setIter.next();
			String SocketAdder = entr.getKey().toString();
			
			System.out.println("Report For - " + SocketAdder);
			
			Iterator<Entry<String, String>> inner = ((Map<String, String>)entr.getValue()).entrySet().iterator();
			while(inner.hasNext()){
				Entry<String, String> entrI = inner.next();
				System.out.println(SocketAdder+":"+entrI.getKey()+" "+entrI.getValue());
			}
		}
		
	}

}
