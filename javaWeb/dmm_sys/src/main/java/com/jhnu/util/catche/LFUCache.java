package com.jhnu.util.catche;

import java.util.HashMap;
import java.util.Iterator;
 
//LFU实现
public class LFUCache<K,V> extends AbstractCacheMap<K, V> {

	public LFUCache(int cacheSize, long defaultExpire) {
        super(cacheSize, defaultExpire);
        cacheMap = new HashMap<K, CacheObject<K,V>>(cacheSize+1) ;
    }
 public int seoCache(int i){
	 return eliminateCache(i);
 }
 @Override
 protected int eliminateCache() {
	 return eliminateCache(0);
 }
    /**
     * 实现删除过期对象 和 删除访问次数最少的对象 
     * 
     */
    protected int eliminateCache(int i) {
        Iterator<CacheObject<K, V>> iterator = cacheMap.values().iterator();
        int count  = 0 ;
        long minAccessCount = Long.MAX_VALUE  ;
        while(iterator.hasNext()){
            CacheObject<K, V> cacheObject = iterator.next();
             
            if(cacheObject.isExpired() ){
                iterator.remove(); 
                count++ ;
                continue ;
            }else{
                minAccessCount  = Math.min(cacheObject.accessCount , minAccessCount)  ;
            }
        }
         
        if(count > 0 ) return count ;
         
        if(minAccessCount != Long.MAX_VALUE ){
             
            iterator = cacheMap.values().iterator();
             
            while(iterator.hasNext()){
                CacheObject<K, V> cacheObject = iterator.next();
                 
                cacheObject.accessCount  -=  minAccessCount ;
                 
                if(cacheObject.accessCount <= i ){
                    iterator.remove();
                    count++ ;
                }
                 
            }
             
        }
         
        return count;
    }
 
}

