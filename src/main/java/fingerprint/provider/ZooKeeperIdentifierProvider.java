package fingerprint.provider;

import com.netflix.curator.RetryPolicy;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.framework.recipes.atomic.AtomicValue;
import com.netflix.curator.framework.recipes.atomic.DistributedAtomicLong;
import com.netflix.curator.framework.recipes.atomic.PromotedToLock;
import com.netflix.curator.retry.RetryNTimes;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.LongIdentifier;

public class ZooKeeperIdentifierProvider implements IdentifierProvider {
    private CuratorFramework curator;
    private DistributedAtomicLong counter;
    
    public ZooKeeperIdentifierProvider(String name) {
        this("localhost:2181", name);
    }
    
    public ZooKeeperIdentifierProvider(String zkHosts, String name) {
        RetryPolicy retryPolicy = new RetryNTimes(3000, 10);
        PromotedToLock mutex = PromotedToLock
            .builder()
            .retryPolicy(retryPolicy)
            .lockPath("/fingerprint/" + name + "/lock")
            .build();
        this.curator = CuratorFrameworkFactory.newClient(zkHosts, retryPolicy);
        this.counter = new DistributedAtomicLong(curator, "/fingerprint/" + name + "/counter", retryPolicy, mutex);
        
        curator.start();
    }
    
    @Override
    public void setID(Identifier identifier) {
        try {
            counter.forceSet(new Long(identifier.getID().longValue()));
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }
    
    @Override
    public Identifier getID() {
        // TODO Auto-generated method stub
        Identifier identifier = null;
        try {
            AtomicValue<Long> value = counter.increment();
            if (value.succeeded()) {
                identifier = new LongIdentifier(value.postValue().longValue());
            }
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        
        return identifier;
    }
}
