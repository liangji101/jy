package com.renren.ntc.video.biz.model;

import java.io.Serializable;

/**
 * @author 张浩 E-mail:hao.zhang@renren-inc.com
 * @date 2012-5-30 下午02:49:43
 *
 * User对象
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

	private String name = "";
	private String email;
	private int    id;
	private String password;
	private String headurl;
//	private String gender = "secret";
//	private Date birthday;
	private String phoneNumber = "" ;

    /**
     * 关注权限 0、自动批准关注 1、自动批准人人网好友 2、亲自批准关注者
     */
    private int attPrivacy;
	
    private int pushType;

    /**
     * 社交同步类型权限,从低位开始，第一位表示人人同步，第二位表示新浪同步(1表示开，0表示关)
     * 从高位开始每四位一组，每组对应低位的具体同步状态
     * 最高四位对应最低一位的同步的状态，也就是人人同步的状态
     * 分别是 观看同步、喜欢同步、评论同步、其他(因为已经上线了，不修改数据库，这里1表示表示关，0表示开,默认为0，是开的)
     */
    
    /**
     *  *发新鲜事的时候根据用户的sycType的状态决定发送哪种新鲜事,目前的策略是低四位作为总开关(目前只有sina和rr的，但是预留了2位),往上每6位作为对应的小开关(预留了3位),顺序为喜欢，评论，拍摄
     *         ^^^111                       ^^^111                            ^^11
     *      sina的小开关                   rr的小开关                总开关（最低位是人人，次低位是sina）
     *      小开关说明：预留|预留|预留|喜欢|评论|拍摄
     *      打开关说明：预留|预留|sina|rr
     */
    private int  sycType;
    
    
    //用户的权值，用于在后台编辑热门用户的时候使用，在user表里面没保存，仅仅保存在rec_user表中
    private int weight;
    //用户是否是热门用户的标志位,用于后台编辑，不保存到数据库
    private int isHot;
    //用户是否已绑定人人的标志位，用于后台编辑界面的不同显示，不保存到数据库
    private int isRRBaned;
    //用户是否已绑定新浪的标志位，用于后台编辑界面的不同显示，不保存到数据库
    private int isSinaBaned;
    public int getIsRRBaned() {
		return isRRBaned;
	}

	public void setIsRRBaned(int isRRBaned) {
		this.isRRBaned = isRRBaned;
	}

	public int getIsSinaBaned() {
		return isSinaBaned;
	}

	public void setIsSinaBaned(int isSinaBaned) {
		this.isSinaBaned = isSinaBaned;
	}

	private int isBan;
    
	public int getIsBan() {
		return isBan;
	}

	public void setIsBan(int isBan) {
		this.isBan = isBan;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public User(){
		
	};
	
	public User(String name,String headurl,String phoneNumber){
		this.name = name;
		this.headurl = headurl;
//		this.gender = gender;
//		this.birthday = birthday;
        if(phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
	}
	
	
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
//	public String getGender() {
//		return gender;
//	}
//	
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	
//	
//	public Date getBirthday() {
//		return birthday;
//	}
//	
//	public void setBirthday(Date birthday) {
//		this.birthday = birthday;
//	}
	
	
	public String getHeadurl() {
		return headurl;
	}
	
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	
	
	public String getName() {
		
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

    public int getAttPrivacy() {
        return attPrivacy;
    }

    public void setAttPrivacy(int attPrivacy) {
        this.attPrivacy = attPrivacy;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }
    
    
    public int getSycType() {
        return sycType;
    }

    public void setSycType(int sycType) {
        this.sycType = sycType;
    }
    
}