package zookeeper.groups;

import java.util.List;

import org.apache.zookeeper.KeeperException;

import zookeeper.watcher.ConnectionWatcher;

public class ListGroup extends ConnectionWatcher {
	public List<String> list(String groupName) throws KeeperException,
			InterruptedException {
		List<String> children = null;
		String path = "/" + groupName;
		try {
			children = zk.getChildren(path, false);
			if (children.isEmpty()) {
				System.out.printf("No members in group %s\n", groupName);
				System.exit(1);
			}
			
		} catch (KeeperException.NoNodeException e) {
			System.out.printf("Group %s does not exist\n", groupName);
			System.exit(1);
		}
		return children;
	}

	public static List<String> main(String[] args) throws Exception {
		ListGroup listGroup = new ListGroup();
		listGroup.connect(args[0]);
		List<String> details = listGroup.list(args[1]);
		listGroup.close();
		return details;
	}
}
