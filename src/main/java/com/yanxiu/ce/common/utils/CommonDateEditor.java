package com.yanxiu.ce.common.utils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: Description:日期属性编辑器
 * @author: tangm
 * @date: 2016年2月16日 
 * @version: 1.0
 */
public class CommonDateEditor extends PropertyEditorSupport{

	private final boolean allowEmpty = true;
	
	private final int exactDateLength = -1;
	
	DateConverter dateConverter = null;
	
	public CommonDateEditor(){
		dateConverter = new DateConverter();
		dateConverter.setPatterns(
			new String[]{
				"yyyy-MM-dd",
				"yyyy-MM-dd HH:mm:ss",
				"dd/MM/yyyy"
			}
		);
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && StringUtils.isBlank(text)) {
			setValue(null);
		}
		else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
			throw new IllegalArgumentException(
					"Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
		}else {
			Date d = (Date)this.dateConverter.convert(Date.class, text);
			setValue(d);
		}
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return value.toString();
	}
	
}
