/** 
 * 消息基类（普通用户 -> 公众帐号） 
 *  
 * @author acer
 * @date 2020-03-27 
 */ 
package com.acecr.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@ToString
public class BaseMessage {
	// 开发者微信号
	private String ToUserName;
	// 发送方帐号（一个OpenID）
	private String FromUserName;
	// 消息创建时间 （整型）
    // 消息类型（text/image/location/link）
	private long CreateTime;
	private String MsgType;
    // 消息id，64位整型 
    private long MsgId;

//	@Override
//    public boolean equals(Object o){
//		if(this == o){
//			return true;
//		}
//		if(o == null || getClass() != o.getClass()){
//			return false;
//		}
//		BaseMessage that = (BaseMessage) o;
//		return Objects.equals(ToUserName,that.ToUserName) && Objects.equals(FromUserName,that.FromUserName) && Objects.equals(CreateTime,that.CreateTime) && Objects.equals(MsgType,that.MsgType) && Objects.equals(MsgId,that.MsgId);
//	}
//
//	@Override
//	public int hashCode(){
//		return Objects.hash(ToUserName,FromUserName,CreateTime,MsgType,MsgId);
//	}
}
