package com.jhkj.mosdc.framework.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.MappingException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import com.jhkj.mosdc.framework.service.EntityService;

public class EntityServiceImpl implements EntityService{
	private static final String RESOURCE_PATTERN = "/**/*.class";
	private String[] packagesToScan;
	private static Map entityMap=new ConcurrentHashMap();
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	@Override
	public Class getEntityBySimpleName(String simpleName) {
		if(entityMap.isEmpty()){
			this.scanPackages();
		}
		return (Class)entityMap.get(simpleName);
	}
	
	/**
	 * Perform Spring-based scanning for entity classes.
	 * @throws ClassNotFoundException 
	 * @see #setPackagesToScan
	 */
	private void scanPackages()  {
		if (this.packagesToScan != null) {
			try {
				for (String pkg : this.packagesToScan) {
					String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
							ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
					Resource[] resources = this.resourcePatternResolver.getResources(pattern);
					MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							MetadataReader reader = readerFactory.getMetadataReader(resource);
							String className = reader.getClassMetadata().getClassName();
							Class classz=Class.forName(className);
							entityMap.put(classz.getSimpleName(), classz);							
						}
					}
				}
			}
			catch (Throwable ex) {
				throw new MappingException("Failed to scan classpath for unlisted classes", ex);
			}
			
		}
	}

	public void setPackagesToScan(String[] packagesToScan) {
		this.packagesToScan = packagesToScan;
	}
	

}
