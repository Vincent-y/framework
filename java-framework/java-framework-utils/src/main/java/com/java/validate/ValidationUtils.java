package com.java.validate;

import com.alibaba.fastjson.JSON;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidationUtils {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static <T> void validate(T obj) {
		if (null == obj) {
			throw new ServiceException("{cause:传入对象为空}");
		}
		
		Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
		if (!CollectionUtils.isEmpty(set)) {
			ValidationResult result = new ValidationResult();
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
			result.setCause("参数校验异常");
			throw new ServiceException(JSON.toJSONString(result));
		}
	}
	
	
	public static <T> void validateParams(T obj) {
		if (null == obj) {
			throw new ServiceException("{cause:传入对象为空}");
		}
		
		Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
		if (!CollectionUtils.isEmpty(set)) {
			String result = "参数校验异常,";
			int i = 0;
			int len = set.size();
			for (ConstraintViolation<T> cv : set) {
				if(i == (len-1)){
					result += cv.getMessage();
				}else{
					result += cv.getMessage() + ",";
				}
				i++;
			}
			throw new ServiceException(result);
		}
	}
	
	public static <T> ValidationResult validateEntity(T obj) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
		if (!CollectionUtils.isEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}

	public static <T> ValidationResult validateProperty(T obj,String propertyName) {
		ValidationResult result = new ValidationResult();
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName, Default.class);
		if (!CollectionUtils.isEmpty(set)) {
			result.setHasErrors(true);
			Map<String, String> errorMsg = new HashMap<String, String>();
			for (ConstraintViolation<T> cv : set) {
				errorMsg.put(propertyName, cv.getMessage());
			}
			result.setErrorMsg(errorMsg);
		}
		return result;
	}
}
