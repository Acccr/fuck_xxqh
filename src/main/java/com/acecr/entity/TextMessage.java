/** 
 * 文本消息 
 *  
 * @author huagang.wang 
 * @date 2020-03-27 
 */ 
package com.acecr.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class TextMessage extends BaseMessage{

	  // 消息内容 
    private String Content;

	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		if(o == null || getClass() != o.getClass() ){
			return false;
		}
		TextMessage that = (TextMessage) o;
		return Objects.equals(Content,that.Content) && Objects.equals(getToUserName(), that.getToUserName()) && Objects.equals(getFromUserName(), that.getFromUserName()) && Objects.equals(getCreateTime(), that.getCreateTime()) && Objects.equals(getMsgType(), that.getMsgType()) && Objects.equals(getMsgId(), that.getMsgId());
	}

	@Override
	public int hashCode(){
		return Objects.hash(this.getToUserName(),this.getFromUserName(),this.getCreateTime(),this.getMsgId(),this.getMsgType(),Content);
	}
}
