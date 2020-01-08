package com.baizhi.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AdminService {
    public HashMap<String, Object> login(String enCode, String username, String password, HttpServletRequest request);
}
