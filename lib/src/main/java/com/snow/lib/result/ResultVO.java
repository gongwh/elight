package com.snow.lib.result;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by SNOW on 2018.01.18.
 */
@Data
public class ResultVO extends ResponseEntity<Body> implements Serializable {
    private static final long serialVersionUID = 5216059160426137256L;

    public ResultVO(HttpStatus status) {
        super(status);
    }

    public ResultVO(Body body) {
        super(body, HttpStatus.OK);
    }

    public ResultVO(Body body, HttpStatus status) {
        super(body, status);
    }

    public Body getBody() {
       return super.getBody();
    }
}
