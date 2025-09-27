package com.tisitha.emarket.service;

import com.tisitha.emarket.dto.Mailbody;

public interface EmailService {

    void sendSimpleMessage(Mailbody mailbody);

}
