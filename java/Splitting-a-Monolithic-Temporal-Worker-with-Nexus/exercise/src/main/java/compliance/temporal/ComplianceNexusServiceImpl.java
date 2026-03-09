package compliance.temporal;

// ═══════════════════════════════════════════════════════════════════
//  TODO 4: Implement the Nexus service handler
// ═══════════════════════════════════════════════════════════════════
//
// This handler receives Nexus requests from the Payments team and
// uses signal-with-start to launch a ComplianceWorkflow.
//
// IMPORTANT: Sync handlers should only contain Temporal primitives
// (workflow starts, signals, queries) — NOT arbitrary business logic.
// The actual compliance check runs inside ComplianceWorkflow's activity.
//
// ── Two annotations: ────────────────────────────────────────────
//   @ServiceImpl(service = ComplianceNexusService.class)  — on the class
//   @OperationImpl                                         — on the handler method
//
// ── What to implement: ──────────────────────────────────────────
//
//   1. Add @ServiceImpl annotation pointing to ComplianceNexusService.class
//   2. Create a checkCompliance() method returning:
//      OperationHandler<ComplianceRequest, ComplianceResult>
//   3. Annotate it with @OperationImpl
//   4. Inside, return WorkflowClientOperationHandlers.sync(...) with a lambda that:
//      a. Creates a ComplianceWorkflow stub (task queue "compliance-risk",
//         workflow ID "compliance-" + input.getTransactionId())
//      b. Uses client.newSignalWithStartRequest() to create a batch
//      c. Adds wf::run (start) and wf::submitRequest with input (signal)
//      d. Calls client.signalWithStart(batch)
//      e. Returns WorkflowStub.fromTyped(wf).getResult(ComplianceResult.class)
//
// ── Pattern: ────────────────────────────────────────────────────
//
//   return WorkflowClientOperationHandlers.sync((ctx, details, client, input) -> {
//       ComplianceWorkflow wf = client.newWorkflowStub(...);
//       BatchRequest batch = client.newSignalWithStartRequest();
//       batch.add(wf::run);
//       batch.add(wf::submitRequest, input);
//       client.signalWithStart(batch);
//       return WorkflowStub.fromTyped(wf).getResult(ComplianceResult.class);
//   });

import compliance.domain.ComplianceRequest;
import compliance.domain.ComplianceResult;
import compliance.temporal.workflow.ComplianceWorkflow;
import io.nexusrpc.handler.OperationHandler;
import io.nexusrpc.handler.OperationImpl;
import io.nexusrpc.handler.ServiceImpl;
import io.temporal.client.BatchRequest;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.nexus.WorkflowClientOperationHandlers;
import shared.nexus.ComplianceNexusService;

// TODO: Add @ServiceImpl(service = ComplianceNexusService.class) annotation
public class ComplianceNexusServiceImpl {

    // TODO: Add @OperationImpl and implement checkCompliance() method
    //       Return: WorkflowClientOperationHandlers.sync(...) with signal-with-start
    public OperationHandler<ComplianceRequest, ComplianceResult> checkCompliance() {
        return null;
    }
}
