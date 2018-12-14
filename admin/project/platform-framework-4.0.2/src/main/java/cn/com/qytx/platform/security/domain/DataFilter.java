package cn.com.qytx.platform.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.qytx.platform.base.domain.CompanyId;


/**
* 规则权限对象
* @author serv
*/
@Entity
@Table(name="tb_data_filter_rule")
public class DataFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="name")
    private String name;
    @CompanyId
    @Column(name="company_id")
    private Integer companyId;

	/**
	 * 文档类型 对应实体的全类名 例如： cn.com.qytx.test.testModel
	 * 文档Class类型
	 *
	 */
    @Column(name="MODEL_CLASS_NAME",length=128)
	protected String modelClassName ;



	/**
	 * 对应规则的引用id，可为角色，部门ID，人员ID，岗位ID
	 */
    @Column(name="RELATION_ID",length=64)
	protected String relationId ;



	/**
	 * 操作类型 CREATE READ PRINGT DELETE UPDATE
	 */
    @Column(name="OPERATION_TYPE",length=6)
	protected String  operationType ;


	/**
	 * JPQL
	 */
    @Column(name="CONDITION_JPQL",length=540)
	protected String conditionJpql ;


    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getConditionJpql() {
        return conditionJpql;
    }

    public void setConditionJpql(String conditionJpql) {
        this.conditionJpql = conditionJpql;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
