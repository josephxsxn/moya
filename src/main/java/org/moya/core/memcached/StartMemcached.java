package org.moya.core.memcached;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zookeeper.groups.JoinGroup;

import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.Key;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap;

public class StartMemcached {

	private static final Log LOG = LogFactory.getLog(StartMemcached.class);

    
	public StartMemcached() {

	}

	public static boolean start() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		LOG.info("Setting up classes");

		
		
		LOG.info("Attempting to start jmemcached server deamon");
		final MemCacheDaemon<LocalCacheElement> daemon = new MemCacheDaemon<LocalCacheElement>();

		CacheStorage<Key, LocalCacheElement> storage;
		InetSocketAddress c = new InetSocketAddress(8555);
		storage = ConcurrentLinkedHashMap.create(
				ConcurrentLinkedHashMap.EvictionPolicy.FIFO, 15000, 67108864);
		daemon.setCache(new CacheImpl(storage));
		daemon.setBinary(false);
		daemon.setAddr(c);
		daemon.setIdleTime(120);
		daemon.setVerbose(true);
		daemon.start();
		LOG.info("jmemcached server deamon started");

		StartJettyTest.main(new String[] {});

		try {
			// Add self in zookeer /moya/ group
			JoinGroup.main(new String[] {
					"172.16.165.155:2181",
					"moya",
					InetAddress.getLocalHost().getHostName() + ":"
							+ c.getPort() });
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;// junk
		}


		return true; // got to this point say true
	}

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		start();
		LOG.info("Exiting StartMemcached");
	}

}
