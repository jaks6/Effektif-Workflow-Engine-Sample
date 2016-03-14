import com.effektif.workflow.api.WorkflowEngine;
import com.effektif.workflow.impl.memory.MemoryConfiguration;

public class Engine {

    private final MemoryConfiguration configuration;
    private final WorkflowEngine workflowEngine;

    public Engine() {
        configuration = new MemoryConfiguration();
        workflowEngine = configuration.getWorkflowEngine();
    }
}
