package org.neo4j.kernel.ha.zookeeper;

import java.util.Map;

import org.neo4j.kernel.impl.ha.Broker;
import org.neo4j.kernel.impl.ha.Master;
import org.neo4j.kernel.impl.ha.SlaveContext;

public class ZooKeeperBroker implements Broker
{
    public ZooKeeperBroker( Map<String, String> config )
    {
        
    }
    
    public Master getMaster()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public SlaveContext getSlaveContext()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
