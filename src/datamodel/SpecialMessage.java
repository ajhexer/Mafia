package datamodel;

public class SpecialMessage extends Message{
    private GameRoles role;
    public SpecialMessage(MessageType title, Object content, GameRoles role) {
        super(title, content);
        this.role = role;
    }

    public GameRoles getRole() {
        return role;
    }
}
