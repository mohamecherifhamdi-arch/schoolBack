package com.school.management.dto;

public class ChatResponse {
    private String reply;
    private String conversationId;
    private String messageId;

    public ChatResponse() {}

    public ChatResponse(String reply, String conversationId, String messageId) {
        this.reply = reply;
        this.conversationId = conversationId;
        this.messageId = messageId;
    }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }
    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
}
