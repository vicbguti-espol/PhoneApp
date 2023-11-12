package model.attributes;

import java.util.concurrent.atomic.AtomicLong;

public class Image extends Attribute {
    private static final long serialVersionUID = 1L;
    private static final AtomicLong atomicRefId = new AtomicLong();
    // transient field is not serialized
    private transient long refId;
    String path;
    
    public Image(){
        refId = atomicRefId.incrementAndGet();
    }

    public Image(String path){
        this();
        this.path = path;
    }
    
    public long getRefId() {
        return refId;
    }
    
    public void setPath(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Image{" + '}';
    }
    
    
    
}

