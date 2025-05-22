package com.example.demo.service;

import com.example.demo.dto.BinaryContent;
import com.example.demo.dto.request.BinaryContentRequest;

public interface BinaryContentStorageService {

    BinaryContent create(BinaryContentRequest binaryContentRequest);

}
