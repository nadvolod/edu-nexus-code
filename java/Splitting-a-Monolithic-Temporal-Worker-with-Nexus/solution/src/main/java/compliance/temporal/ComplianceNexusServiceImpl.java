package compliance.temporal;

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

/**
 * Nexus service handler — receives cross-team calls from Payments.
 *
 * This sync handler uses only Temporal primitives: signal-with-start
 * launches a ComplianceWorkflow and delivers the request in one atomic step.
 * Business logic lives in the workflow's activity, not here.
 */
@ServiceImpl(service = ComplianceNexusService.class)
public class ComplianceNexusServiceImpl {

    @OperationImpl
    public OperationHandler<ComplianceRequest, ComplianceResult> checkCompliance() {
        return WorkflowClientOperationHandlers.sync((ctx, details, client, input) -> {
            ComplianceWorkflow wf = client.newWorkflowStub(
                    ComplianceWorkflow.class,
                    WorkflowOptions.newBuilder()
                            .setTaskQueue("compliance-risk")
                            .setWorkflowId("compliance-" + input.getTransactionId())
                            .build());

            BatchRequest batch = client.newSignalWithStartRequest();
            batch.add(wf::run);
            batch.add(wf::submitRequest, input);
            client.signalWithStart(batch);

            // Block until the workflow completes and return its result
            return WorkflowStub.fromTyped(wf).getResult(ComplianceResult.class);
        });
    }
}
