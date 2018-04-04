package networking;

import java.io.Serializable;

public class Request implements Serializable {
    private Object body;
    private RequestType requestType;

    public Request(Object body, RequestType requestType) {
        this.body = body;
        this.requestType = requestType;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
