package ir.ronad.courierManager.common.errors;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.HashMap;
import java.util.Map;

@Getter
public class NotFoundException extends AbstractThrowableProblem {

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }

    private static final long serialVersionUID = 1L;


    public NotFoundException(String title) {
        super(ErrorConstants.NOT_FOUND_TYPE, title, Status.NOT_FOUND, null, null, null);
    }

    public NotFoundException(String defaultMessage, String entityName, String errorKey) {
        super(ErrorConstants.NOT_FOUND_TYPE, defaultMessage, Status.NOT_FOUND, null, null, null, getAlertParameters(entityName, errorKey));
    }
}
