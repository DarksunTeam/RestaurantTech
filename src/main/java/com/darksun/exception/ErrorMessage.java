package com.darksun.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage implements Serializable {
	@Serial
	private static final long serialVersionUID = -8455391240577954769L;

	private HttpStatus    statusCode;
	private LocalDateTime timestamp;
	private String        message;
}
