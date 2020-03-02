package com.shopping.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 0002 15:40
 * @Description: 系统参数获取
 */

@Service
public class ConstantMap {

    private static final Logger log = LoggerFactory.getLogger(ConstantMap.class);

    private Map<String, Object> map = new HashMap<String, Object>() ;

    public Map<String, Object> getMap(){
        return this.map ;
    }

    @Autowired
    private SystemargsService systemargsService;


    @PostConstruct
    public void init(){
        log.info("Init SystemArgs ==> ConstantMap.") ;
        Map<String, Object> tMap = this.systemargsService.findAllMap() ;
        Map<String, Object> frontMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : tMap.entrySet()) {
            this.put(entry.getKey(), entry.getValue()) ;
            if((entry.getKey().contains("article"))){
                frontMap.put(entry.getKey(), entry.getValue()) ;
            }
            if((entry.getKey().contains("front"))){
                frontMap.put(entry.getKey(), entry.getValue()) ;
            }
        }
        map.put("frontMap", frontMap) ;
        log.info("Init virtualCoinType ==> ConstantMap.") ;
    }

    public synchronized void put(String key,Object value){
        log.info("ConstantMap put key:"+key+",value:"+value+".") ;
        map.put(key, value) ;
    }

    public Object get(String key){
        return map.get(key) ;
    }

    public String getString(String key){
        return String.valueOf(map.get(key)) ;
    }

    public String getString(String key, String value){
        String val = getString(key);
        return Objects.isNull(val) ? value : val;
    }

    public int getInt(String key){
        return Integer.valueOf(this.getString(key));
    }

    public int getInt(String key, int value){
        String valStr = this.getString(key);
        return StringUtils.hasText(valStr) ? Integer.valueOf(valStr) : value;
    }

    public double getDouble(String key){
        return Double.valueOf(this.getString(key));
    }

    public double getDouble(String key, double value){
        String valStr = this.getString(key);
        return StringUtils.hasText(valStr) ? Double.valueOf(valStr) : value;
    }
}
