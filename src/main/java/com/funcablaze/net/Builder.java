package com.funcablaze.net;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
import org.json.JSONObject;

public class Builder {

    private StringBuilder Info;
    private Map<String, String> map;

    public Builder(Client.MessageType type, String Ip, JSONObject content) {
        toMap(type, Ip, content);
        toInfo();
    }

    public Builder(String content) {
        map = new HashMap<>();
        /*
          https://regexr-cn.com/
         */
        String pattern = "/Send:([a-z]+):(\\d+\\.\\d+\\.\\d+\\.\\d+):\"([\\s\\S]*)\"/gi";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        if (m.find()) {
            toMap(Client.MessageType(m.group(1)), m.group(2), new JSONObject(m.group(3)));
            toInfo();
        } else {
            map = null;
        }
    }

    private void toMap(Client.MessageType type, String Ip, JSONObject content) {
        map = new HashMap<>();
        map.put("type", Client.MessageType(type));
        map.put("ip", Ip);
        map.put("content", content.toString());
    }

    private void toInfo() {
        Info = new StringBuilder()
                .append("Send").append(':')
                .append(map.get("type")).append(':')
                .append(map.get("ip")).append(':')
                .append('"').append(map.get("content")).append('"');
    }

    public String getString() {
        return Info.toString();
    }

    public Map<String, String> getMap() {
        return map;
    }
}
