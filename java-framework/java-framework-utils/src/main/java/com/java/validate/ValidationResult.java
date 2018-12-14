package com.java.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
	 /**校验结果是否有错**/
	  private boolean hasErrors;
	  /** 原因 **/
	  private String cause;
	  /**校验错误信息**/
	  private Map<String,String> errorMsg;
}
