package compliance.temporal.workflow;

import compliance.domain.ComplianceRequest;
import compliance.domain.ComplianceResult;
import compliance.temporal.activity.ComplianceActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

/**
 * Waits for a ComplianceRequest signal, then runs the compliance check activity.
 *
 * This workflow exists so the Nexus sync handler only uses Temporal primitives
 * (signal-with-start), keeping business logic inside the activity where it belongs.
 */
public class ComplianceWorkflowImpl implements ComplianceWorkflow {

    private ComplianceRequest pendingRequest = null;

    private final ComplianceActivity complianceActivity = Workflow.newActivityStub(
            ComplianceActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(30))
                    .build());

    @Override
    public ComplianceResult run() {
        // Wait until the signal delivers the request
        Workflow.await(() -> pendingRequest != null);

        // Run the compliance check as an activity
        return complianceActivity.checkCompliance(pendingRequest);
    }

    @Override
    public void submitRequest(ComplianceRequest request) {
        this.pendingRequest = request;
    }
}
