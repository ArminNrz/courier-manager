package ir.ronad.courierManager.common.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;


    private final String objectName;

    private final String field;

    private final String message;
}
