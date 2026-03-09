package compliance.temporal;

// ═══════════════════════════════════════════════════════════════════
//  TODO 3: Create the Compliance team's worker
// ═══════════════════════════════════════════════════════════════════
//
// This is the standard CRAWL worker pattern, with ONE new step:
//   registering the Nexus service implementation.
//
// CRAWL pattern — Workers CRAWL before they run:
//   C — Connect to Temporal
//   R — Register (no workflows in this worker)
//   A — Activities (none — compliance logic lives in the Nexus handler)
//   W — Wire the Nexus service handler  ← NEW
//   L — Launch
//
// ── The key new method: ─────────────────────────────────────────
//   worker.registerNexusServiceImplementation(
//       new ComplianceNexusServiceImpl(new ComplianceChecker())
//   )
//
// Compare to what you already know:
//   Activities:  worker.registerActivitiesImplementations(...)
//   Nexus:       worker.registerNexusServiceImplementation(...)
//   Same shape, different method name.
//
// ── Task queue: "compliance-risk" ───────────────────────────────
// This MUST match what you set as --target-task-queue when creating
// the Nexus endpoint via the CLI. If they don't match, calls fail.
//
// ── Imports you'll need: ────────────────────────────────────────
//   import compliance.ComplianceChecker;
//   import io.temporal.client.WorkflowClient;
//   import io.temporal.serviceclient.WorkflowServiceStubs;
//   import io.temporal.worker.Worker;
//   import io.temporal.worker.WorkerFactory;

import compliance.ComplianceChecker;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import shared.nexus.ComplianceNexusService;

public class ComplianceWorkerApp {

    private static final String TASK_QUEUE = "compliance-risk";

    public static void main(String[] args) {
        // TODO: C — Connect to Temporal
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);

        // TODO: R — Create factory and worker on "compliance-risk"
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(TASK_QUEUE);

        // TODO: W — Create ComplianceChecker + register Nexus service implementation
        worker.registerNexusServiceImplementation(
                new ComplianceNexusServiceImpl(new ComplianceChecker()));

        // TODO: L — Start the factory and print a startup banner
        factory.start();
    }
}
