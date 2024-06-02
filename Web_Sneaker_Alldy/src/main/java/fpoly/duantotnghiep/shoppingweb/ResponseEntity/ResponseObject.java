package fpoly.duantotnghiep.shoppingweb.ResponseEntity;

public class ResponseObject {
    private String title;
    private String mess;
    private Object object;

    public ResponseObject(String title,String mess, Object object) {
        this.title = title;
        this.mess = mess;
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
