package com.easysoft.logserver.logs.model;

import java.io.Serializable;
import java.util.Date;

import com.easysoft.commons.annotation.LikeQuery;
import com.easysoft.commons.annotation.PrimaryKey;
import com.easysoft.commons.annotation.TableName;

/**
 * 
 * Title: AuditLog 
 * Description: 日志类 Company: easysoft.ltd
 * @author IvanHsu
 * @date 2019年6月5日
 */
@TableName("t_audit_log")
@PrimaryKey("id")
@SuppressWarnings("serial")
public class AuditLog implements Serializable {
	private String id;
	private String clientIp;// 请求者IP
	private String reqMethod;// 请求方法
	private String reqClass;
	private String reqType;// 请求方式
	private String reqUri;// 请求的地址
	@LikeQuery
	private String operator;// 操作人
	private String operatorId;// 操作人ID
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private String totalTime;// 耗时
	@LikeQuery
	private String result;// 请求结果
	private String auditType;// 类型
	private String remark;// 操作的结果
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getReqClass() {
		return reqClass;
	}

	public void setReqClass(String reqClass) {
		this.reqClass = reqClass;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqUri() {
		return reqUri;
	}

	public void setReqUri(String reqUri) {
		this.reqUri = reqUri;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
