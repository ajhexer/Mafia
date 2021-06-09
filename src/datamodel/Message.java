package datamodel;

public class Message {
    MessageType title;
    Object content;
    public Message(MessageType title, Object content){
        this.title = title;
        this.content = content;
    }
}
