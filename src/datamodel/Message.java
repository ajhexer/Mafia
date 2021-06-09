package datamodel;

import java.io.Serializable;

public class Message implements Serializable {
    MessageType title;
    Object content;
    public Message(MessageType title, Object content){
        this.title = title;
        this.content = content;
    }

    public MessageType getTitle() {
        return title;
    }

    public Object getContent() {
        return content;
    }
}
