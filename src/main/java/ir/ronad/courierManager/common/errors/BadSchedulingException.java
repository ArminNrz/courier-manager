package ir.ronad.courierManager.common.errors;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BadSchedulingException extends AbstractThrowableProblem {

    private static Map<String, Object> getParameters(Long vehicleId, Integer bundleIndex) {
        Map<String, Object> parameters = new HashMap<>();
        if (vehicleId != null) parameters.put("vehicleId", vehicleId);
        if (bundleIndex != null) parameters.put("bundleIndex", bundleIndex);
        return parameters;
    }

    private static final long serialVersionUID = 1L;


    private final Long vehicleId;

    private final Integer bundleIndex;

    public BadSchedulingException(String title, Long vehicleId, Integer bundleIndex) {
        super(ErrorConstants.SCHEDULING_TYPE, title, Status.BAD_REQUEST, null, null, null,
                getParameters(vehicleId, bundleIndex));
        this.vehicleId = vehicleId;
        this.bundleIndex = bundleIndex;
    }
}
