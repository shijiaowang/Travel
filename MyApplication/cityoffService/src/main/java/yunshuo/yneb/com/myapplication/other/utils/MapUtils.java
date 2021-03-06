package yunshuo.yneb.com.myapplication.other.utils;


import java.util.HashMap;
import java.util.Map;

import yunshuo.yneb.com.myapplication.IVariable;

/**
 * Created by wangyang on 2016/7/29 0029.
 */
public class MapUtils {
     static Map<String,String> stringMap=new HashMap<>();
    public static Builder Build(){
        stringMap.clear();
        return new Builder();
    }
    public static class Builder{
        public Builder addKey(){
            stringMap.put(IVariable.KEY,GlobalUtils.getKey());
            return this;
        }
        public Builder addMyId(){
            stringMap.put(IVariable.MY_ID,GlobalUtils.getUserInfo().getId());
            return this;
        }
        public Builder addUserName(String userName){
            stringMap.put(IVariable.USERNAME,userName);
            return this;
        }
        public Builder addPassword(String password){
            stringMap.put(IVariable.PASSWORD,password);
            return this;
        }
        public Builder addJsonTravel(String json){
            stringMap.put(IVariable.JSON_TRAVEL,json);
            return this;
        }
        public Builder addPageSize(int pageSize){
            stringMap.put(IVariable.PAGE_SIZE,pageSize+"");
            return this;
        }
        public Builder addPageSize(){
            stringMap.put(IVariable.PAGE_SIZE,IVariable.pageCount+"");
            return this;
        }
        public Builder addOldPassWord(String oldPass){
            stringMap.put(IVariable.OLD_PASSWORD,oldPass);
            return this;
        }
        public Builder addNewPassWord(String newPass){
            stringMap.put(IVariable.NEW_PASSWORD,newPass);
            return this;
        }
        public Builder addPictureId(String pictureId){
            stringMap.put(IVariable.PICTURE_ID,pictureId);
            return this;
        }
        public Builder addType(String type){
            stringMap.put(IVariable.TYPE,type);
            return this;
        }
        public Builder addClass(String clazz){
            stringMap.put(IVariable.CLASS,clazz+"");
            return this;
        }
        public Builder addTel(String tel){
            stringMap.put(IVariable.TEL,tel+"");
            return this;
        }
        public Builder addTypeId(String typeId){
            stringMap.put(IVariable.TYPE_ID,typeId+"");
            return this;
        }
        public Builder addProvince(String province){
            stringMap.put(IVariable.PROVINCE,province);
            return this;
        }
        public Builder addCity(String city){
            stringMap.put(IVariable.CITY,city);
            return this;
        }
        public Builder addAddress(String address){
            stringMap.put(IVariable.ADDRESS,address);
            return this;
        }
        public Builder addAId(String aid){
            stringMap.put(IVariable.A_ID,aid);
            return this;
        }
        public Builder addRUserId(String rUserId){
            stringMap.put(IVariable.R_USER_ID,rUserId);
            return this;
        }
        public Builder addId(String id){
            stringMap.put(IVariable.ID,id);
            return this;
        }
        public Builder addCId(String cid){
            stringMap.put(IVariable.C_ID,cid);
            return this;
        }
        public Builder addCode(String code){
            stringMap.put(IVariable.CODE,code);
            return this;
        }
        public Builder addMoney(float money){
            stringMap.put(IVariable.MONEY,money+"");
            return this;
        }
        public Builder addCoupon(String coupon){
            stringMap.put(IVariable.COUPON,coupon);
            return this;
        }
        public Builder addTpId(String tpId){
            stringMap.put(IVariable.TP_ID,tpId);
            return this;
        }
        public Builder addInform(String inform){
            stringMap.put(IVariable.INFORM,inform);
            return this;
        }
        public Builder addUserId(){
            try {
                stringMap.put(IVariable.USER_ID,GlobalUtils.getUserInfo().getId()+"");
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("userId不见啦");
            }
            return this;
        }

        public Builder addT_Id(String tId){
            stringMap.put(IVariable.T_ID,tId);
            return this;
        }
        public Builder addtId(String tId){
            stringMap.put(IVariable.TID,tId);
            return this;
        }
        public Builder addTitle(String title){
            stringMap.put(IVariable.TITLE,title);
            return this;
        }
        public Builder addLabel(String label){
            stringMap.put(IVariable.LABEL,label);
            return this;
        }
        public Builder addFId(String fId){
            stringMap.put(IVariable.F_ID,fId);
            return this;
        }
        public Builder addFroumId(String froumId){
            stringMap.put(IVariable.FORUM_ID,froumId);
            return this;
        }
        public Builder addPId(String pId){
            stringMap.put(IVariable.PID,pId);
            return this;
        }
        public Builder addContent(String content){
            stringMap.put(IVariable.CONTENT,content);
            return this;
        }
        public Builder addRId(String rId){
            stringMap.put(IVariable.R_ID,rId);
            return this;
        }
        public Builder addNextPage(String nextPage){
            stringMap.put(IVariable.NEXT_PAGE,nextPage);
            return this;
        }
        public Builder addCount(int count){
            stringMap.put(IVariable.COUNT,count+"");
            return this;
        }
        public Builder addNickName(String nickName){
            stringMap.put(IVariable.NICK_NAME,nickName);
            return this;
        }
        public Builder add(String key,String value){
            stringMap.put(key,value);
            return this;
        }

        public Map<String,String> end(){
            return stringMap;
        }

        public Builder addSex(String sex){
            stringMap.put("sex",sex);
            return this;
        }


    }
}
