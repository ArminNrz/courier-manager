package ir.ronad.courierManager.config;

import io.github.jhipster.web.util.HeaderUtil;
import ir.ronad.courierManager.common.errors.BadRequestAlertException;
import ir.ronad.courierManager.common.errors.ErrorConstants;
import ir.ronad.courierManager.common.errors.FieldErrorVM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

	private static final String FIELD_ERRORS_KEY = "fieldErrors";
	private static final String MESSAGE_KEY = "message";
	private static final String PATH_KEY = "path";
	private static final String VIOLATIONS_KEY = "violations";

	@Value("${spring.application.name}")
	private String applicationName;

	/**
	 * Post-process the Problem payload to add the message key for the front-end if
	 * needed.
	 */
	@Override
	public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
		if (entity == null) {
			return entity;
		}
		Problem problem = entity.getBody();
		if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
			return entity;
		}
		ProblemBuilder builder = Problem.builder()
				.withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE
						: problem.getType())
				.withStatus(problem.getStatus()).withTitle(problem.getTitle())
				.with(PATH_KEY, request.getNativeRequest(HttpServletRequest.class).getRequestURI());

		if (problem instanceof ConstraintViolationProblem) {
			builder.with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations()).with(MESSAGE_KEY,
					ErrorConstants.ERROR_VALIDATION);
		} else {
			builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail())
					.withInstance(problem.getInstance());
			problem.getParameters().forEach(builder::with);
			if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
				builder.with(MESSAGE_KEY, "error.http." + problem.getStatus().getStatusCode());
			}
		}
		return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
	}

	@Override
	public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			@Nonnull NativeWebRequest request) {
		BindingResult result = e.getBindingResult();
		List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream()
				.map(f -> new FieldErrorVM(f.getObjectName().replaceFirst("DTO$", ""), f.getField(), f.getCode()))
				.collect(Collectors.toList());

		Problem problem = Problem.builder().withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
				.withTitle("Method argument not valid").withStatus(defaultConstraintViolationStatus())
				.with(MESSAGE_KEY, ErrorConstants.ERROR_VALIDATION).with(FIELD_ERRORS_KEY, fieldErrors).build();
		return create(e, problem, request);
	}

	@ExceptionHandler
	public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException e,
																  NativeWebRequest request) {
		return create(e, request, HeaderUtil.createFailureAlert(applicationName, false, e.getEntityName(),
				e.getErrorKey(), e.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<Problem> handleNoSuchElementException(NoSuchElementException e, NativeWebRequest request) {
		Problem problem = Problem.builder().withStatus(Status.NOT_FOUND).withTitle("Not found!").build();
		return create(e, problem, request);
	}

	@ExceptionHandler
	public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException e, NativeWebRequest request) {
		Problem problem = Problem.builder().withStatus(Status.CONFLICT)
				.with(MESSAGE_KEY, ErrorConstants.ERROR_CONCURRENCY_FAILURE).build();
		return create(e, problem, request);
	}
}
