package com.subhajit.service;

import com.subhajit.response.ApiResponse;

public interface ChatbotService {

    ApiResponse getCoinDetails(String prompt) throws Exception;

    String simpleChat(String prompt);
}
