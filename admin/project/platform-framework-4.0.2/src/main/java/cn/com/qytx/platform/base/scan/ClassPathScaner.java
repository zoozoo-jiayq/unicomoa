package cn.com.qytx.platform.base.scan;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

public class ClassPathScaner {  
  
    private static final String     DEFAULT_RESOURCE_PATTERN = "**/*.class";  
    private ResourcePatternResolver resourcePatternResolver  = new PathMatchingResourcePatternResolver();  
    private MetadataReaderFactory   metadataReaderFactory    = new CachingMetadataReaderFactory(this.resourcePatternResolver);  
    private String                  resourcePattern          = DEFAULT_RESOURCE_PATTERN;  
    private final List<TypeFilter>  includeFilters           = new LinkedList<TypeFilter>();  
    private final List<TypeFilter>  excludeFilters           = new LinkedList<TypeFilter>();  
  
    public void addIncludeFilter(TypeFilter includeFilter) {  
        this.includeFilters.add(includeFilter);  
    }  
  
    public void addExcludeFilter(TypeFilter excludeFilter) {  
        this.excludeFilters.add(0, excludeFilter);  
    }  
  
    public void resetFilters() {  
        this.includeFilters.clear();  
        this.excludeFilters.clear();  
    }  
  
    public String packageToRelativedPath(String basePackage) {  
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));  
    }  
  
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {  
        for (TypeFilter tf : this.excludeFilters) {  
            if (tf.match(metadataReader, this.metadataReaderFactory)) { return false; }  
        }  
        for (TypeFilter tf : this.includeFilters) {  
            if (tf.match(metadataReader, this.metadataReaderFactory)) { return true; }  
        }  
        return false;  
    }  
  
    public Set<MetadataReader> findCandidateClasss(String... basePackage) {  
        Set<MetadataReader> candidates = new LinkedHashSet<MetadataReader>();  
        try {  
            for (String s : basePackage) {  
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + packageToRelativedPath(s) + "/"  
                        + this.resourcePattern;  
                //if (logger.isDebugEnabled()) logger.debug("扫描包:" + packageSearchPath);  
                Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);  
                for (Resource resource : resources) {  
                    if (resource.isReadable()) {  
                        try {  
                            MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);  
                            if (isCandidateComponent(metadataReader)) {  
                                candidates.add(metadataReader);  
                               // if (logger.isDebugEnabled()) logger.debug("匹配:" + metadataReader.getClassMetadata().getClassName());  
                            }  
                        } catch (Throwable ex) {  
                            // throw new Exception("类符合条件但读取失败: " + resource, ex);  
                        }  
                    } else {  
                        //if (logger.isDebugEnabled()) logger.debug("因为扫描到的类无法读取元数据,忽略: " + resource);  
                    }  
                }  
            }  
        } catch (IOException ex) {  
        }  
        return candidates;  
    }  
  
    public List<Resource> findCandidateFiles(String patterns, String... basePackage) {  
        List<Resource> resources = new LinkedList<Resource>();  
        try {  
            for (String p : basePackage) {  
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + packageToRelativedPath(p) + "/" + patterns;  
                Resource[] current = this.resourcePatternResolver.getResources(packageSearchPath);  
                if (current.length == 0) continue;  
                resources.addAll(Arrays.asList(current));  
            }  
            return resources;  
        } catch (IOException ex) {  
            // throw new IOException("扫描时发生 I/O 异常", ex);  
            return null;  
        }  
    }  
}  
