package com.ruikun.sys.investment.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.constants.ShiroConstant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {
    /**
     * 密码MD5加密
     *
     * @param pw   密码
     * @param salt 密码盐
     * @return
     */
    public static String saltMd5(String pw, String salt) {
        Object simpleHash = new SimpleHash(ShiroConstant.HASHALGORITHM_NAME, pw,
                salt, ShiroConstant.HASHITERATIONS);
        return simpleHash.toString();
    }

    /**
     * 生n位随机数
     *
     * @return String
     */
    public static String getRandom(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            String random = (int) (10 * (Math.random())) + "";
            sb.append(random);
        }
        return sb.toString();
    }

    /**
     * 生n位随机字母
     *
     * @return String
     */
    public static String getRandomLetter(int n) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            char random = (char) (Math.random() * 26 + 'a');
            sb.append(random);
        }
        return sb.toString();
    }

    /**
     * 生成唯一标号(非并发情况下)
     *
     * @return String
     */
    public static String getUniqueCode() {
        //获取6位随机数
        String random = getRandom(6);
        //获取当前时间-毫秒级
        long currentTime = System.currentTimeMillis();
        return currentTime + random;
    }

    /**
     * map转string （拼接url）
     *
     * @param map
     * @return
     */
    public static String mapToString(Map<String, Object> map) {
        String mapStr = "";
        for (Map.Entry<String, Object> mMap : map.entrySet()) {
            mapStr += "&" + mMap.getKey() + "=" + mMap.getValue();
        }
        if (mapStr.length() > 0) {
            return mapStr.substring(1);
        }
        return null;
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取图片验证码(随机生成)
     */
    public static String getCreateImgCode() {
        return getRequest().getSession().getAttribute(Constants.IMG_CODE).toString().toLowerCase();
    }


    /**
     * 两个数组转json  一个数组 key  一个数组 value
     */
    public static String twoArrayToJson(List<String> keyList, String[] valueArr) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (int i = 0; i < keyList.size(); i++) {
            jsonObject = new JSONObject();
            for (int j = 0; j < valueArr.length; j++) {
                if (i == j) {
                    jsonObject.put(keyList.get(i), valueArr[j]);
                    jsonArray.add(jsonObject);
                }
            }
        }
        return jsonArray.toString();
    }

    /**
     * 生成 n 为随机码
     *
     * @return
     */
    public static String getRandomchar(int n) {
        String[] strArr = new String[]{"a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuffer redomString = new StringBuffer();
        int count = strArr.length;
        for (int i = 0; i < n; i++) {
            int index = (int) (Math.random() * count);
            redomString.append(strArr[index]);
        }
        return redomString.toString();
    }

    /**
     * 克隆对象非空属性值
     *
     * @return
     */
    public static void cloneObject(Object o1, Object o2) {
        Field[] fields1 = o1.getClass().getDeclaredFields();
        Class clazz = o2.getClass();
        for (int i = 0, len = fields1.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields1[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields1[i].isAccessible();
                // 修改访问控制权限
                fields1[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                try {
                    o = fields1[i].get(o1);
                    if (o != null) {
                        PropertyDescriptor pd = new PropertyDescriptor(fields1[i].getName(), clazz);
                        Method setMethod = pd.getWriteMethod();//获得set方法
                        setMethod.invoke(o2, o);//此处为执行该Object对象的set方法
                    }
                    //System.err.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 恢复访问控制权限
                fields1[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @Description：校验指定的对象的指定属性是否为空,如果有一个为空，就返回false
     * @Author： zhujiangwei
     * @Date： 2019/4/22 18:18
     */
    public static boolean checkObjIsNull(Object obj, List<String> fildNames) throws Exception {
        Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            String name = f.getName();
            if (fildNames.contains(name)) {
                f.setAccessible(true); // 修改访问控制权限
                String type = f.getGenericType().toString(); //获取属性类型
                Object val = f.get(obj);// 得到此属性的值
                if ("class java.lang.String".equals(type)) {
                    if (val == null || "".equals(val.toString())) {
                        flag = false;
                        break;
                    }
                } else if ("class java.lang.Integer".equals(type)) {
                    if (val == null || 0 == (Integer) val) {
                        flag = false;
                        break;
                    }
                } else if ("class java.lang.Double".equals(type)) {
                    if (val == null || 0d == (Double) val) {
                        flag = false;
                        break;
                    }
                } else {
                    if (val == null) {
                        flag = false;
                        break;
                    }
                }

            }
        }
        return flag;
    }

    /**
     * 获取业务编号
     *
     * @param businessType 编号类型
     * @return
     */
    public static String getBusinessCode(String businessType) {
        String code = "";
        //获取6位随机数
        String random = getRandom(4);
        //获取当前时间
        String currentDate = DateTimeUtil.getFormatNowTime("yyyyMMdd");
        if (Constants.ROOM_CONTRACT_CODE.equals(businessType)) { // 房源合同
            code = Constants.ROOM_CONTRACT_CODE + currentDate + random;
        } else if (Constants.CAR_PLACE_CONTRACT_CODE.equals(businessType)) { // 车位合同
            code = Constants.CAR_PLACE_CONTRACT_CODE + currentDate + random;
        } else if (Constants.WORK_PLACE_CONTRACT_CODE.equals(businessType)) { // 工位合同
            code = Constants.WORK_PLACE_CONTRACT_CODE + currentDate + random;
        } else if (Constants.RECEIPT_BILL_SK_CODE.equals(businessType)) { // 收款账单
            code = Constants.RECEIPT_BILL_SK_CODE + currentDate + random;
        } else if (Constants.RECEIPT_BILL_FK_CODE.equals(businessType)) { // 付款账单
            code = Constants.RECEIPT_BILL_FK_CODE + currentDate + random;
        } else if (Constants.COMPANY_CODE.equals(businessType)) { // 公司编号
            code = Constants.COMPANY_CODE + currentDate + random;
        }
        return code;
    }

    // 去除小输掉
    public static String trimPoint(String str) {
        int index = str.indexOf(".");
        if (index != -1) {
            str = str.substring(0, index);
        }
        return str;
    }

    public static void main(String[] args) {
        LinkedHashMap contractFieldMap = Constants.CONTRACT_FIELD_MAP;
        Iterator iter = contractFieldMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println("key: "+ key + " val: " + val);
        }
    }

}
