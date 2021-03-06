package com.yogeshprasad.restgrpc.example.grpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.yogeshprasad.restgrpc.example.BookList;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BookGrpcController {

    private final BookServiceGrpcClient bookServiceGrpcClient;

    @RequestMapping(value = "/test_grpc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test(@RequestParam(value = "compact", required = false, defaultValue = "false") boolean compact) {

        BookList books = bookServiceGrpcClient.createBooks(TestDataUtil.getGrpcTestData());

        if (compact) {
            return new ResponseEntity<>("Grpc success response from V2", HttpStatus.OK);
        } else {
            String jsonString = "";
            try{
                jsonString = JsonFormat.printer().print(books);
            }catch (InvalidProtocolBufferException ex){
                ex.printStackTrace();
            }

            return new ResponseEntity<>(jsonString, HttpStatus.OK);
        }
    }
}
