package cn.com.qytx.platform.base.scan;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.util.Assert;
/** 
* 重新实现JPA容器托管实体工厂代替LocalContainerEntityManagerFactoryBean以方便扫描打jar后的models 
*  
* @author 
*/  
public class ContainerEntityManagerFactoryBean extends AbstractEntityManagerFactoryBean {  

  private DefaultPersistenceUnitManager persistenceUnitManager;  

  private PersistenceUnitInfo persistenceUnitInfo;  
  private DataSource dataSource;  
  private String scanPackages = "";  

  // ----------------------------------------------------------------------------------------------  
  @Override  
  public PersistenceUnitInfo getPersistenceUnitInfo() {  
      return this.persistenceUnitInfo;  
  }  

  @Override  
  public String getPersistenceUnitName() {// 指定实体工厂使用特定的持久化单元  
      if (this.persistenceUnitInfo != null) { return this.persistenceUnitInfo.getPersistenceUnitName(); }  
      return super.getPersistenceUnitName();  
  }  

  @Override  
  public DataSource getDataSource() {  
      if (dataSource != null) return dataSource;  
      else if (this.persistenceUnitInfo != null) return this.persistenceUnitInfo.getNonJtaDataSource();  
      else return this.persistenceUnitManager.getDefaultDataSource();  
  }  

  @Override  
  protected EntityManagerFactory createNativeEntityManagerFactory() throws PersistenceException {  
      Assert.isTrue((persistenceUnitManager != null || persistenceUnitInfo != null), "需要设置persistenceUnitManager或persistenceUnitInfo属性");  

      if (persistenceUnitInfo == null) this.persistenceUnitInfo = determinePersistenceUnitInfo(persistenceUnitManager);  
      JpaVendorAdapter jpaVendorAdapter = getJpaVendorAdapter();  
      if (jpaVendorAdapter != null && this.persistenceUnitInfo instanceof MutablePersistenceUnitInfo) {  
          ((MutablePersistenceUnitInfo) this.persistenceUnitInfo).setPersistenceProviderPackageName(jpaVendorAdapter  
                  .getPersistenceProviderRootPackage());  
      }  
      PersistenceProvider provider = getPersistenceProvider();  
      if (logger.isInfoEnabled()) logger.info("正在构建 JPA 容器 EntityManagerFactory, 持久化单元为:'" + this.persistenceUnitInfo.getPersistenceUnitName()  
              + "'");  

      scanEntitys();// 扫描实体并在创建实体工厂前添加到所使用的持久化单元里  

      this.nativeEntityManagerFactory = provider.createContainerEntityManagerFactory(this.persistenceUnitInfo, getJpaPropertyMap());  

      postProcessEntityManagerFactory(this.nativeEntityManagerFactory, this.persistenceUnitInfo);  
      return this.nativeEntityManagerFactory;  
  }  

  // ----------------------------------------------------------------------------------------------  
  protected PersistenceUnitInfo determinePersistenceUnitInfo(PersistenceUnitManager persistenceUnitManager) {  
      if (getPersistenceUnitName() != null) return persistenceUnitManager.obtainPersistenceUnitInfo(getPersistenceUnitName());  
      else return persistenceUnitManager.obtainDefaultPersistenceUnitInfo();  
  }  

  protected void postProcessEntityManagerFactory(EntityManagerFactory emf, PersistenceUnitInfo pui) {  
  }  

  public void setPersistenceUnitManager(DefaultPersistenceUnitManager persistenceUnitManager) {  
      this.persistenceUnitManager = persistenceUnitManager;  
  }  

  // 直接指定PersistenceUnitInfo就不需要指定PersistenceUnitManager,但应指定DataSource  
  public void setPersistenceUnitInfo(PersistenceUnitInfo persistenceUnitInfo) {  
      this.persistenceUnitInfo = persistenceUnitInfo;  
  }  

  public void setDataSource(DataSource dataSource) {  
      this.dataSource = dataSource;  
  }  

  public void setScanPackages(String scanPackages) {// null或不指定表示不扫描,空白表示扫描非jar目录  
      this.scanPackages = scanPackages;  
  }  

  /** 扫描classpath中的(包括分别打包在不同jar中的)实体类并添加到PersistenceUnitInfo中以创建EntityManagerFactory */  
  private void scanEntitys() {  
      String[] pgs = scanPackages.split(",");
      if (pgs.length > -1) {  
          ClassPathScaner p = new ClassPathScaner();  
          // p.addIncludeFilter(new AssignableTypeFilter(TypeFilter.class));  
          // Set<MetadataReader> bd = p.findCandidateClasss("org.springframework");  
          p.addIncludeFilter(new AnnotationTypeFilter(Entity.class));  
          Set<MetadataReader> bd = p.findCandidateClasss(pgs);  
          List<String> managedClass = persistenceUnitInfo.getManagedClassNames();  
          for (MetadataReader b : bd) {  
              if (!(managedClass.contains(b.getClassMetadata().getClassName()))) {  
                  managedClass.add(b.getClassMetadata().getClassName());  
              }  
          }  
      }  
  }

public String getScanPackages() {
	return scanPackages;
}  
  
}  