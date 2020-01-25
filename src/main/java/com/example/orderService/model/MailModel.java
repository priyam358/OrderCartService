package com.example.orderService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.util.Map;
@Data
@AllArgsConstructor
@ToString
public class MailModel {
    private String name;
    private String subject;
    private String content;
    private Map<String, String> model;
}
