package zetta.exception;

import java.util.List;

public class ExceptionResponseDto {

    private String error;
    private int status;
    private List<String> messages;

    public ExceptionResponseDto(String error, int status, List<String> messages) {
        this.error = error;
        this.status = status;
        this.messages = messages;
    }

    public ExceptionResponseDto(String error, int status, String messages) {
        this.error = error;
        this.status = status;
        this.messages = List.of(messages);
    }

    // Getters and setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}