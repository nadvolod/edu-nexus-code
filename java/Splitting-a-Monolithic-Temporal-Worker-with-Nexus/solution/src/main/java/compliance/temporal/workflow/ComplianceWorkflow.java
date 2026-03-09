package compliance.temporal.workflow;

import compliance.domain.ComplianceRequest;
import compliance.domain.ComplianceResult;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

/**
 * Workflow that wraps the compliance check activity.
 *
 * Receives a ComplianceRequest via signal, runs the check, and returns the result.
 * The Nexus sync handler uses signal-with-start to launch this workflow and
 * deliver the request in a single atomic operation.
 */
@WorkflowInterface
public interface ComplianceWorkflow {

    @WorkflowMethod
    ComplianceResult run();

    @SignalMethod
    void submitRequest(ComplianceRequest request);
}
