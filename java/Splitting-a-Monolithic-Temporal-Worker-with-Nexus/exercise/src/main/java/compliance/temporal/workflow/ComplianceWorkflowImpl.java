package compliance.temporal.workflow;

// ═══════════════════════════════════════════════════════════════════
//  TODO 3: Implement the ComplianceWorkflow
// ═══════════════════════════════════════════════════════════════════
//
// This workflow:
//   1. Waits for a signal delivering the ComplianceRequest
//   2. Runs ComplianceActivity.checkCompliance() on the request
//   3. Returns the ComplianceResult
//
// ── What to implement: ──────────────────────────────────────────
//
//   - A field: ComplianceRequest pendingRequest = null
//   - An activity stub for ComplianceActivity (with a 30s startToCloseTimeout)
//   - run(): Use Workflow.await(() -> pendingRequest != null),
//            then call complianceActivity.checkCompliance(pendingRequest)
//   - submitRequest(): Set pendingRequest = request
//
// ── Key pattern: ────────────────────────────────────────────────
//
//   Workflow.await(() -> pendingRequest != null)
//
//   This blocks the workflow until the signal arrives. It's durable —
//   if the worker restarts, the workflow replays and waits again.

import compliance.domain.ComplianceRequest;
import compliance.domain.ComplianceResult;
import compliance.temporal.activity.ComplianceActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class ComplianceWorkflowImpl implements ComplianceWorkflow {

    private ComplianceRequest pendingRequest = null;

    private final ComplianceActivity complianceActivity = Workflow.newActivityStub(
            ComplianceActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(30))
                    .build());

    @Override
    public ComplianceResult run() {
        // TODO: Wait for signal using Workflow.await(() -> pendingRequest != null)

        // TODO: Call complianceActivity.checkCompliance(pendingRequest) and return the result
        return null;
    }

    @Override
    public void submitRequest(ComplianceRequest request) {
        // TODO: Set pendingRequest = request
    }
}
