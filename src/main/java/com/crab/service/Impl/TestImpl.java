package com.crab.service.Impl;

import com.crab.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxp
 * @version sota；
 */
@Service
public class TestImpl implements TestService {

    @Override
    public String say() {
        return "hello";
    }
}
