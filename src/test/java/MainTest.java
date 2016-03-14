import com.effektif.workflow.api.WorkflowEngine;
import com.effektif.workflow.api.activities.NoneTask;
import com.effektif.workflow.api.activities.ReceiveTask;
import com.effektif.workflow.api.model.TriggerInstance;
import com.effektif.workflow.api.model.WorkflowId;
import com.effektif.workflow.api.workflow.ExecutableWorkflow;
import com.effektif.workflow.api.workflowinstance.WorkflowInstance;
import com.effektif.workflow.impl.bpmn.BpmnMapper;
import com.effektif.workflow.impl.memory.MemoryConfiguration;
import org.junit.Test;

import java.io.InputStreamReader;


/**
 * Created by Jakob on 14.03.2016.
 */
public class MainTest {
    MemoryConfiguration configuration = new MemoryConfiguration();
    WorkflowEngine workflowEngine = configuration.getWorkflowEngine();


    @Test
    public void engineInstantiation() {

        //WORKFLOW DEFINITION ( MANUAL)
        ExecutableWorkflow workflow = new ExecutableWorkflow()
                .sourceWorkflowId("release")
                .name("Software release")
                .activity("Move open issues", new NoneTask()
                        .transitionToNext())
                .activity("Check continuous integration", new ReceiveTask());


        //WORKFLOW DEPLOYMENT
        WorkflowId id = workflowEngine
                .deployWorkflow(workflow)
                .checkNoErrors()
                .getWorkflowId();

        //WORKFLOW INSTANCE START
        WorkflowInstance workflowInstance = workflowEngine
                .start(new TriggerInstance()
                        .workflowId(id)
                        .data("v1", "hello1"));

    }
    @Test
    public void bpmnFileWorkflowDeployment() {

        //Approach 1
        InputStreamReader isr = new InputStreamReader(
                this.getClass().getResourceAsStream("/EffektifSample.bpmn"));

        BpmnMapper mapper = BpmnMapper.createBpmnMapperForTest();
        ExecutableWorkflow wfFromMapper = (ExecutableWorkflow) mapper.readFromReader(isr);

        //WORKFLOW DEPLOYMENT
        WorkflowId id = workflowEngine
                .deployWorkflow(wfFromMapper)
                .checkNoErrors()
                .getWorkflowId();

        //WORKFLOW INSTANCE START
        WorkflowInstance workflowInstance = workflowEngine
                .start(new TriggerInstance()
                        .workflowId(id)
                        .data("v2", "hello2"));
    }
}
