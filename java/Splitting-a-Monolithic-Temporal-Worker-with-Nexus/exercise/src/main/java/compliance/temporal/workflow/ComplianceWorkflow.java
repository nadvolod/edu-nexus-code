package compliance.temporal.workflow;

// ═══════════════════════════════════════════════════════════════════
//  TODO 2: Define the ComplianceWorkflow interface
// ═══════════════════════════════════════════════════════════════════
//
// This workflow wraps the compliance check activity. The Nexus handler
// uses signal-with-start to launch it and deliver the request atomically.
//
// ── What to add: ────────────────────────────────────────────────
//
//   1. Add @WorkflowInterface annotation
//   2. Add a @WorkflowMethod: ComplianceResult run()
//   3. Add a @SignalMethod:   void submitRequest(ComplianceRequest request)
//
// The signal delivers the request; the workflow method runs the check.
//
// ── Template: ───────────────────────────────────────────────────
//
//   @WorkflowInterface
//   public interface ComplianceWorkflow {
//       @WorkflowMethod
//       ComplianceResult run();
//
//       @SignalMethod
//       void submitRequest(ComplianceRequest request);
//   }

import compliance.domain.ComplianceRequest;
import compliance.domain.ComplianceResult;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

// TODO: Add @WorkflowInterface annotation
public interface ComplianceWorkflow {

    // TODO: Add @WorkflowMethod annotation
    ComplianceResult run();

    // TODO: Add @SignalMethod annotation
    void submitRequest(ComplianceRequest request);
}
