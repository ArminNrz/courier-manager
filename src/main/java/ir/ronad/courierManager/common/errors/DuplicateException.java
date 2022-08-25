package ir.ronad.courierManager.common.errors;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.HashMap;
import java.util.Map;

@Getter
public class DuplicateException extends AbstractThrowableProblem {

    private static Map<String, Object> getParameters(String duplicateField) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", ErrorConstants.ERROR_DUPLICATE +
                (duplicateField != null ? "." + duplicateField : ""));
        return parameters;
    }

    private static final long serialVersionUID = 1L;


    private final String duplicateField;

    public DuplicateException(String title, String duplicateField) {
        super(ErrorConstants.CONFLICT_TYPE, title, Status.CONFLICT, null, null, null,
                getParameters(duplicateField));
        this.duplicateField = duplicateField;
    }
}
