package com.upday.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author jalajchawla
 */
@Data
public class UpdayResponse implements Serializable {
    private static final long serialVersionUID = 7789284253047806745L;
    private Object data;
    private boolean success = true;
    private String message = "Request Processed Successfully";
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private Date timestamp = new Date();

}
