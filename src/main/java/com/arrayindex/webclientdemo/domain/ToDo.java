package com.arrayindex.webclientdemo.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ToDo {
    private int id;
    private String userId;
    private String title;
    private boolean completed;
}
