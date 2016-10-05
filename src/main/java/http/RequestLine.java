package http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HttpRequestUtils;

public class RequestLine {
    private static final Logger log = LoggerFactory.getLogger(RequestLine.class);

    //Enum , GET,POST로 구성됨.
    private HttpMethod method;

    private String path;

    private Map<String, String> params;

    //POST /user/create HTTP/1.1
    public RequestLine(String requestLine) {
        log.debug("request line : {}", requestLine);
        String[] tokens = requestLine.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException(requestLine + "이 형식에 맞지 않습니다.");
        }
        //GET인지 POST인지 확인함.
        method = HttpMethod.valueOf(tokens[0]);
        
        //POST방식
        if (method.isPost()) {
            path = tokens[1];
            params = new HashMap<String, String>();
            return;
        }

        //GET방식
        int index = tokens[1].indexOf("?");
      //GET방식인데 뒤에 파라미터가 없는 경우.
        if (index == -1) {
            path = tokens[1];
            params = new HashMap<String, String>();
        } else {
        	//GET방식인데 뒤에 파라미터가 있는 경우.
            path = tokens[1].substring(0, index);
            params = HttpRequestUtils.parseQueryString(tokens[1].substring(index + 1));
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public boolean isPost() {
        return method.isPost();
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
