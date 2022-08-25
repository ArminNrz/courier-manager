package ir.ronad.courierManager.common.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorConstants {

    public static final String PROBLEM_BASE_URL = "https://ronad.ir/problem";

    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI SCHEDULING_TYPE = URI.create(PROBLEM_BASE_URL + "/scheduling");
    public static final URI CONFLICT_TYPE = URI.create(PROBLEM_BASE_URL + "/conflict");
    public static final URI NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/not-found");

    public static final String ERROR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERROR_VALIDATION = "error.validation";
    public static final String ERROR_DUPLICATE = "error.duplicate";
}
